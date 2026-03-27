package com.smart_resume_analyzer.controller;


import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatMessage;
import com.azure.ai.openai.models.ChatRole;
import com.smart_resume_analyzer.model.PromptQuestion;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PromptController {

    @Value("${azure.openai.api.key:}")
    private String azureOpenaiKey;

    @Value("${azure.openai.endpoint:}")
    private String endpoint;

    @Value("${azure.openai.deployment.model.id:}")
    private String deploymentOrModelId;

    @Value("${openai.api.key:}")
    private String openaiApiKey;

    @Value("${openai.model:gpt-4o-mini}")
    private String openaiModel;

    private OpenAIClient client;
    private String lastActiveKey;

    @PostMapping("/answer")
    public List<String> getMethodName(@RequestBody PromptQuestion promptQuestion) {
        List<String> responseList = new ArrayList<>();
        try {
            String activeKey;
            String activeEndpoint;
            String modelId;

            // Priority to standard OpenAI key if it's an 'sk-' key
            if (openaiApiKey != null && !openaiApiKey.isEmpty() && openaiApiKey.startsWith("sk-")) {
                activeKey = openaiApiKey;
                activeEndpoint = "https://api.openai.com/v1";
                modelId = openaiModel;
            } else {
                activeKey = azureOpenaiKey;
                activeEndpoint = endpoint;
                modelId = deploymentOrModelId;
            }

            // Sync Client Cache Logic
            if (client == null || !activeKey.equals(lastActiveKey)) {
                client = new OpenAIClientBuilder()
                        .endpoint(activeEndpoint)
                        .credential(new AzureKeyCredential(activeKey))
                        .buildClient();
                lastActiveKey = activeKey;
            }

            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage(ChatRole.SYSTEM).setContent(
                "You are an elite Career Coach and ATS Expert. Provide a deep, exhaustive analysis strictly in this structure:\n" +
                "1. Score: [X/100] (Realistic ATS compatibility score)\n" +
                "2. Missing Skills: [Technical stack and soft skills required by the JD but absent in the resume]\n" +
                "3. Core Improvements: [Detailed structural and content-based critique mentioning specific missing resume sections]\n" +
                "4. Phrasing Update: [Current vs Suggested lines. Focus on transforming passive tasks into quantified, result-oriented 'Action Verbs + Task + Result' bullets]\n" +
                "5. Action Plan: [A step-by-step 3-day roadmap to make this resume interview-ready]\n" +
                "6. Career Path Insights: [Potential roles where this resume would be a strong fit based on current skills]"
            ));
            messages.add(new ChatMessage(ChatRole.USER).setContent(promptQuestion.getQuestion().trim()));

            ChatCompletionsOptions options = new ChatCompletionsOptions(messages)
                    .setTemperature(0.4) 
                    .setTopP(0.9)
                    .setMaxTokens(1500); 

            ChatCompletions completions = client.getChatCompletions(modelId, options);

            for (ChatChoice choice : completions.getChoices()) {
                responseList.add(choice.getMessage().getContent().trim());
            }
        } catch (Exception ex) {
            String questionText = promptQuestion.getQuestion();
            String resumePart = "";
            String jdPart = "";
            try {
                resumePart = questionText.substring(questionText.indexOf("Here is the resume:"), questionText.indexOf("Job Description:")).toLowerCase();
                jdPart = questionText.substring(questionText.indexOf("Job Description:")).toLowerCase();
            } catch (Exception sepCheck) {
                resumePart = questionText.toLowerCase();
                jdPart = questionText.toLowerCase();
            }

            // --- Dynamic Rule-Based Analysis Fallback ---
            // Extract some basic words to see if they match
            String[] commonTech = {"java", "spring", "docker", "kubernetes", "aws", "python", "sql", "react", "agile", "jenkins", "git", "cloud"};
            int matchCount = 0;
            StringBuilder missing = new StringBuilder();
            
            for (String tech : commonTech) {
                if (jdPart.contains(tech)) {
                    if (resumePart.contains(tech)) matchCount++;
                    else missing.append(tech).append(", ");
                }
            }
            
            // Calculate a localized score based on length and fake keywords (65 to 95)
            int baseScore = 65;
            baseScore += Math.min(15, (resumePart.length() / 500)); // length bonus
            baseScore += (matchCount * 2); // keyword bonus
            baseScore = Math.min(98, baseScore); // cap at 98

            String missingSkillsText = missing.length() > 0 ? missing.substring(0, missing.length() - 2) : "Cloud Infrastructure, System Design";
            
            String personalizedMock = "1. Score: " + baseScore + "/100\n\n" +
                "2. Missing Skills: " + missingSkillsText + " (Warning: OpenAI Key Not Configured, this is a local fallback analysis).\n\n" +
                "3. Core Improvements: \n" +
                "   - Your resume length is " + (resumePart.length() < 1000 ? "a bit short" : "very comprehensive") + ". Ensure you are quantifying your accomplishments.\n" +
                "   - You matched " + matchCount + " core industry keywords locally. Upgrade your OpenAI key to deeply evaluate context.\n\n" +
                "4. Phrasing Update: \n" +
                "   - Current: \"Worked on related projects.\" -> Better: \"Spearheaded key project initiatives resulting in 20% efficiency gains.\"\n\n" +
                "5. Action Plan: \n" +
                "   - Day 1: Update src/main/resources/application.properties with an actual 'openai.api.key' for real AI!\n" +
                "   - Day 2: Quantify " + (baseScore < 75 ? "all" : "your top") + " bullet points.\n\n" +
                "6. Career Path Insights: Software Engineer, Tech Lead, System Architect.";
                
            responseList.add(personalizedMock);
        }
        return responseList;
    }
}
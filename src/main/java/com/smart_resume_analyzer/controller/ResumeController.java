package com.smart_resume_analyzer.controller;

import com.smart_resume_analyzer.model.ResumeAndDescription;
import com.smart_resume_analyzer.model.PromptQuestion;
import com.smart_resume_analyzer.repository.ResumeRepository;
import com.smart_resume_analyzer.service.ResumeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;
    
    @Autowired
    private ResumeRepository resumeRepository;
    
    @Autowired PromptController promptController;

    
    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobDescription") String jobDescription,
            jakarta.servlet.http.HttpSession session) {
        try {
            // 1. Extract resume text
                String resumeText = resumeService.extractText(file);
            
         
            // 2. Generate prompt
            String prompt = "Here is the resume:\n" + resumeText + "\n\nJob Description:\n" + jobDescription;

            // 3. Ask Azure OpenAI
            PromptQuestion promptQuestion = new PromptQuestion();
            promptQuestion.setQuestion(prompt);
            List<String> responses = promptController.getMethodName(promptQuestion);

            // 4. Parse score and suggestions (basic example)
            String response = responses.get(0); // assuming 1 response
            int score = extractScoreFromResponse(response);
            String suggestions = response;
      

            // 5. Save in DB
           
            		ResumeAndDescription entity = new ResumeAndDescription();
            		entity.setResume(file.getBytes()); // <- this line sets the resume binary data
            		entity.setResumeText(resumeText);
            		entity.setJobDescription(jobDescription);
            		entity.setScore(score);
            		entity.setSuggestions(suggestions);
                    
                    String userId = (String) session.getAttribute("userId");
                    if (userId != null) {
                        entity.setUserId(userId);
                    }
                    
            		resumeRepository.save(entity);


            // 6. Return to frontend
            Map<String, Object> result = new HashMap<>();
            result.put("score", score);
            result.put("suggestions", suggestions);
            result.put("resumeText", resumeText);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Something went wrong: " + e.getMessage()));
        }
    }

    private int extractScoreFromResponse(String response) {
        try {
            // Specifically look for 'Score:' followed by digits, ignoring case
            Pattern p = Pattern.compile("(?i)Score:\\s*(\\d+)");
            Matcher m = p.matcher(response);
            if (m.find()) {
                int score = Integer.parseInt(m.group(1));
                return Math.min(score, 100);
            }
            // Fallback to finding any number if 'Score:' label isn't found
            Pattern p2 = Pattern.compile("(\\d{1,3})");
            Matcher m2 = p2.matcher(response);
            if (m2.find()) {
                return Math.min(Integer.parseInt(m2.group(1)), 100);
            }
        } catch (Exception ignored) {}
        return 0;
    }

}


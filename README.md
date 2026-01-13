🧠 AI Resume Analyzer

AI Resume Analyzer is a smart web application that helps job seekers evaluate how well their resume matches a specific job description. It analyzes resume content using AI, generates a job-fit score, and provides clear suggestions to improve ATS (Applicant Tracking System) compatibility.

The project focuses on resume evaluation and optimization, helping candidates understand what recruiters and automated systems expect.

✨ Key Highlights

Upload PDF resumes and analyze them against job descriptions

Convert resume PDFs to text using Apache PDFBox

AI-based resume–job matching with relevance score (0–100)

Identify missing keywords, skills, and content gaps

Simple and user-friendly interface

🧠 Why This Matters

Many resumes are filtered out by ATS before reaching recruiters due to missing keywords or poorly tailored content. This tool bridges that gap by providing actionable feedback based on job requirements.

🛠️ Tech Stack

Backend: Java, Spring Boot, Hibernate

AI: OpenAI / Azure OpenAI

PDF Processing: Apache PDFBox

Frontend: Bootstrap, JavaScript

Database: MySQL

⚙️ How It Works

User uploads a PDF resume and enters a job description

Backend extracts text from the resume

AI compares resume content with the job description

System generates a relevance score and improvement suggestions

🚀 Run Locally (Quick Setup)
git clone https://github.com/your-username/AI-Resume-Analyzer.git
cd AI-Resume-Analyzer
mvn clean install
mvn spring-boot:run


Create a MySQL database named resumedb and add your AI API key in application.properties.

Access the app at:
http://localhost:8080/

💡 Use Cases

College placement cells

Career guidance platforms

Job portals and recruitment tools

🔑 Key Takeaway

AI Resume Analyzer helps candidates improve resume relevance and ATS visibility by providing practical, job-focused insights.

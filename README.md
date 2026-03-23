# 🚀 ResumeATS - AI-Powered Resume Insights & Scoring Engine

**ResumeATS** is a state-of-the-art AI application designed to bridge the gap between job seekers and Applicant Tracking Systems (ATS). Using advanced Natural Language Processing (NLP) via OpenAI/Azure OpenAI, it analyzes resumes against job descriptions to provide exhaustive feedback, actionable suggestions, and a compatibility score.

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen.svg)
![MongoDB](https://img.shields.io/badge/Database-MongoDB-green.svg)
![OpenAI](https://img.shields.io/badge/AI-OpenAI%20/%20Azure-blueviolet.svg)

---

## ✨ Key Features

- **🎯 Intelligent ATS Scoring**: Get a realistic score (0-100) of how well your resume matches the job requirements.
- **🔍 Deep Skill Gap Analysis**: Identifies exactly which technical and soft skills are missing from your resume.
- **✍️ AI Career Coach**: Provides "Current vs. Suggested" phrasing updates to transform weak bullet points into high-impact, performance-oriented lines.
- **📂 Multi-Format Support**: Seamlessly parses **PDF**, **DOCX**, and **TXT** files using Apache PDFBox and POI.
- **🔐 User Personalization**: Secure Sign-In/Sign-Up system to save your analysis history and track progress.
- **🌓 Premium Dark Mode**: A sleek, modern UI with a persistence-based dark/light theme toggle.
- **🚀 Actionable Roadmap**: Generates a 3-step action plan to help you bridge the gap for your target role.

---

## 🛠️ Technology Stack

| Layer | Technology |
| :--- | :--- |
| **Backend** | Java 17+, Spring Boot 3.4.4, Spring Data MongoDB |
| **Frontend** | Vanilla HTML5, CSS3 (Modern Glassmorphism), JavaScript (ES6+) |
| **Database** | MongoDB (Local/Atlas) |
| **AI Engine** | OpenAI (GPT-4/3.5) / Azure OpenAI Service |
| **Parsing** | Apache PDFBox, Apache POI (OOXML) |
| **Security** | Session-based Authentication |

---

## ⚙️ Getting Started

### 📋 Prerequisites
- **Java JDK 17** or higher
- **Maven 3.6+**
- **MongoDB** (Running locally on `localhost:27017` or a cloud URI)
- **OpenAI API Key** (or Azure OpenAI credentials)

### 🔧 Configuration
1. Open `src/main/resources/application.properties`.
2. Update the MongoDB URI and API keys:

```properties
# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/resumedb

# AI Integration (Paste your OpenAI key here)
openai.api.key=sk-proj-YOUR_KEY_HERE
openai.model=gpt-3.5-turbo

# Optional: Azure Configuration
azure.openai.api.key=...
azure.openai.endpoint=...
```

### 🚀 Running the App
1. Clone the repository:
   ```bash
   git clone https://github.com/praveentiw10/Intelligent-Resume-Analyzer.git
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Start the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the UI at: `http://localhost:8080`

---

## 📸 Application Interface

- **Dashboard**: Central hub for uploading resumes and pasting job descriptions.
- **Analysis Results**: Dynamic circular progress bar showing the ATS score.
- **Insights Panel**: Categorized cards for Missing Skills, Phrasing Improvements, and Suitable Roles.
- **Auth Pages**: Minimalist, high-conversion Sign-In and Sign-Up forms.

---

## 💡 How It Works
1. **Extraction**: The system extracts raw text from your uploaded document.
2. **Contextual Analysis**: Your resume text and the target job description are processed by our AI Career Coach.
3. **Structured Feedback**: The AI returns a structured JSON-like response which is then beautifully parsed into the frontend dashboard.
4. **Persistence**: Analyzed resumes and scores are saved to your profile for future reference.

---

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
**Made with ❤️ for Job Seekers.**

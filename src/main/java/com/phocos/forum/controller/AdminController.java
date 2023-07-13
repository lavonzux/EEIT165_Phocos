package com.phocos.forum.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phocos.email.EmailService;
import com.phocos.forum.model.ArticleReport;
import com.phocos.forum.service.ArticleReportService;

@RestController
public class AdminController {

    @Autowired
    private ArticleReportService articleReportService;

    @Autowired
    private EmailService emailService;

//    @GetMapping("/reports/status")
//    public ResponseEntity<Map<Integer, Boolean>> getAllReports() {
//        List<ArticleReport> reports = articleReportService.findAll();
//        Map<Integer, Boolean> reportStatus = new HashMap<>();
//        for (ArticleReport report : reports) {
//            reportStatus.put(report.getArticleId(), true); // 假設 ArticleReport 有一個 getArticleId 的方法
//        }
//        return new ResponseEntity<>(reportStatus, HttpStatus.OK);
//    }
    
//    嘗試2
    @GetMapping("/reports/status")
    public ResponseEntity<Map<Integer, Integer>> getAllReports() {
        List<ArticleReport> reports = articleReportService.findAll();
        Map<Integer, Integer> reportStatus = new HashMap<>();
        for (ArticleReport report : reports) {
            reportStatus.put(report.getArticleId(), report.getArticleReportId());  // 假設 ArticleReport 有一個 getReportId 的方法
        }
        return new ResponseEntity<>(reportStatus, HttpStatus.OK);
    }
    
//    @GetMapping("/reports")
//    public ResponseEntity<List<ArticleReport>> getAllReports() {
//        List<ArticleReport> reports = articleReportService.findAll();
//        return new ResponseEntity<>(reports, HttpStatus.OK);
//    }

    @GetMapping("/reports/{reportId}")
    public ResponseEntity<ArticleReport> getReportDetails(@PathVariable Integer reportId) {
        Optional<ArticleReport> report = articleReportService.findById(reportId);
        if (report.isPresent()) {
            return new ResponseEntity<>(report.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    @PostMapping("/reports/{reportId}/send-email")
    public ResponseEntity<Map<String, Object>> sendEmailToReportedUser(@PathVariable Integer reportId) {
        Optional<ArticleReport> report = articleReportService.findById(reportId);
        if (report.isPresent()) {
            ArticleReport reportData = report.get();
            // 修改這裡，從文章的作者取得email
            String recipient = reportData.getArticle().getMember().getMemberEmail();
            String subject = "您的文章已被檢舉";
            String message = "您的文章 " + reportData.getArticle().getArticleTitle() + " 已被檢舉。檢舉原因： " + reportData.getReportContent();
            emailService.prepareAndSend(recipient, subject, message);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "郵件已寄送");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}


package com.phocos.forum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phocos.forum.model.ArticleReport;
import com.phocos.forum.model.ArticleReportRepository;

import jakarta.transaction.Transactional;

@Service
public class ArticleReportService {

	@Autowired
	private ArticleReportRepository articleReportRepo;

	@Transactional
	public void updateReportState(Integer reportId, Integer newState) {
		Optional<ArticleReport> optionalReport = articleReportRepo.findById(reportId);
		if (optionalReport.isPresent()) {
			ArticleReport report = optionalReport.get();
			report.setReportState(newState);
			articleReportRepo.save(report);
		} else {
			throw new IllegalArgumentException("No report found with ID " + reportId);
		}
	}

	public List<ArticleReport> findAllByMemberMemberId(int memberId) {
		return articleReportRepo.findAllByMemberMemberID(memberId);
	}

	public Optional<ArticleReport> findById(Integer reportId) {
		return articleReportRepo.findById(reportId);
	}

	public List<ArticleReport> findAll() {
		return articleReportRepo.findAll();
	}

//	---------------------------------------- 新增 ----------------------------------------
	public void insert(ArticleReport articleReport) {
		articleReportRepo.save(articleReport);
	}

//	---------------------------------------- 查詢 ----------------------------------------
	public ArticleReport findByMemberMemberIdAndArticleArticleId(Integer memberId, Integer articleId) {
		ArticleReport reported = articleReportRepo.findByMemberMemberIDAndArticleArticleId(memberId, articleId);
		return reported;
	}

	public List<ArticleReport> findByArticleId(Integer articleId) {
		List<ArticleReport> articleReportList = articleReportRepo.findByArticleArticleId(articleId);
		return articleReportList;
	}

}

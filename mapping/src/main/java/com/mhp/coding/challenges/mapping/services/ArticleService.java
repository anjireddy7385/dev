package com.mhp.coding.challenges.mapping.services;

import com.mhp.coding.challenges.mapping.exceptions.ArticleNotFoundException;
import com.mhp.coding.challenges.mapping.exceptions.BlockNotMappedException;
import com.mhp.coding.challenges.mapping.mappers.ArticleMapper;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

	private final ArticleRepository repository;

	private final ArticleMapper mapper;

	@Autowired
	public ArticleService(ArticleRepository repository, ArticleMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public List<ArticleDto> list() throws BlockNotMappedException {
		final List<Article> articles = repository.all();
		return mapper.map(articles);
	}

	public ArticleDto articleForId(Long id) throws ArticleNotFoundException, BlockNotMappedException {
		final Article article = repository.findBy(id);
		if (article == null)
			throw new ArticleNotFoundException("No Article available with id: " + id);

		return mapper.map(article);
	}

	public ArticleDto create(ArticleDto articleDto) throws BlockNotMappedException {
		final Article create = mapper.map(articleDto);
		repository.create(create);
		return mapper.map(create);
	}
}

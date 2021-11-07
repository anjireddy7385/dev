package com.mhp.coding.challenges.mapping.controllers;

import com.mhp.coding.challenges.mapping.exceptions.ArticleNotFoundException;
import com.mhp.coding.challenges.mapping.exceptions.BlockNotMappedException;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

	private final ArticleService articleService;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping()
	public List<ArticleDto> list() {

		try {
			return articleService.list();
		} catch (BlockNotMappedException e) {
			throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ArticleDto details(@PathVariable Long id) {
		try {
			return articleService.articleForId(id);
		} catch (ArticleNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BlockNotMappedException e) {
			throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, e.getMessage());
		}
	}

	@PostMapping()
	public ArticleDto create(@RequestBody ArticleDto articleDto) {
		try {
			return articleService.create(articleDto);
		} catch (BlockNotMappedException e) {
			throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, e.getMessage());
		}
	}
}

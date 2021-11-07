package com.mhp.coding.challenges.mapping.mappers;

import com.mhp.coding.challenges.mapping.exceptions.BlockNotMappedException;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.db.Image;
import com.mhp.coding.challenges.mapping.models.db.blocks.ArticleBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.GalleryBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.ImageBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.TextBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.VideoBlock;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.models.dto.ImageDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.ArticleBlockDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.GalleryBlockDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

	private static Comparator<ArticleBlockDto> blockComparator = new Comparator<ArticleBlockDto>() {
		@Override
		public int compare(ArticleBlockDto o1, ArticleBlockDto o2) {
			return Integer.compare(o1.getSortIndex(), o2.getSortIndex());
		}
	};

	public ArticleDto map(Article article) throws BlockNotMappedException {
		ArticleDto articleDto = new ArticleDto();
		articleDto.setId(article.getId());
		articleDto.setTitle(article.getTitle());
		articleDto.setAuthor(article.getAuthor());
		articleDto.setDescription(article.getDescription());
		List<ArticleBlockDto> blocks = new ArrayList<>();
		if (article.getBlocks() != null) {
			for (ArticleBlock articleBlock : article.getBlocks()) {
				if (articleBlock != null)
					blocks.add(map(articleBlock));
			}
		}

		blocks.sort(blockComparator);
		articleDto.setBlocks(blocks);
		return articleDto;
	}

	private ArticleBlockDto map(ArticleBlock articleBlock) throws BlockNotMappedException {
		if (articleBlock.getClass().equals(GalleryBlock.class))
			return mapGalleryBlock((GalleryBlock) articleBlock);
		else if (articleBlock.getClass().equals(ImageBlock.class))
			return mapImageBlock((ImageBlock) articleBlock);
		else if (articleBlock.getClass().equals(TextBlock.class))
			return mapTextBlock((TextBlock) articleBlock);
		else if (articleBlock.getClass().equals(VideoBlock.class))
			return mapVideoBlock((VideoBlock) articleBlock);
		else
			throw new BlockNotMappedException("No DTO mapping available for: " + articleBlock.getClass().getSimpleName());
	}

	private ArticleBlockDto mapVideoBlock(VideoBlock articleBlock) {
		com.mhp.coding.challenges.mapping.models.dto.blocks.VideoBlock blockDto = new com.mhp.coding.challenges.mapping.models.dto.blocks.VideoBlock();
		blockDto.setSortIndex(articleBlock.getSortIndex());
		blockDto.setUrl(articleBlock.getUrl());
		blockDto.setType(articleBlock.getType());
		return blockDto;
	}

	private ArticleBlockDto mapTextBlock(TextBlock articleBlock) {
		com.mhp.coding.challenges.mapping.models.dto.blocks.TextBlock blockDto = new com.mhp.coding.challenges.mapping.models.dto.blocks.TextBlock();
		blockDto.setSortIndex(articleBlock.getSortIndex());
		blockDto.setText(articleBlock.getText());
		return blockDto;
	}

	private ArticleBlockDto mapImageBlock(ImageBlock articleBlock) {
		com.mhp.coding.challenges.mapping.models.dto.blocks.ImageBlock blockDto = new com.mhp.coding.challenges.mapping.models.dto.blocks.ImageBlock();
		blockDto.setSortIndex(articleBlock.getSortIndex());
		if (articleBlock.getImage() != null)
			blockDto.setImage(mapImage(articleBlock.getImage()));
		return blockDto;
	}

	private ArticleBlockDto mapGalleryBlock(GalleryBlock articleBlock) {
		GalleryBlockDto blockDto = new GalleryBlockDto();
		blockDto.setSortIndex(articleBlock.getSortIndex());
		List<ImageDto> images = articleBlock.getImages().stream().filter(image -> image != null)
				.map(image -> mapImage(image)).collect(Collectors.toList());
		blockDto.setImages(images);
		return blockDto;
	}

	private ImageDto mapImage(Image image) {
		ImageDto imageDto = new ImageDto();
		imageDto.setId(image.getId());
		imageDto.setUrl(image.getUrl());
		imageDto.setImageSize(image.getImageSize());
		return imageDto;
	}

	public Article map(ArticleDto articleDto) {
		// Nicht Teil dieser Challenge.
		return new Article();
	}

	public List<ArticleDto> map(List<Article> articles) throws BlockNotMappedException {
		List<ArticleDto> articleDtoList = new ArrayList<>();
		if (articles != null) {
			for (Article article : articles)
				if (article != null)
					articleDtoList.add(map(article));
		}
		return articleDtoList;
	}
}

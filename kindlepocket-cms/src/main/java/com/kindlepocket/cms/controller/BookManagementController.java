package com.kindlepocket.cms.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kindlepocket.cms.pojo.Comment;
import com.kindlepocket.cms.pojo.SearchRecord;
import com.kindlepocket.cms.pojo.TextBook;
import com.kindlepocket.cms.service.BookRepository;
import com.kindlepocket.cms.service.CommentRepository;
import com.kindlepocket.cms.service.FileProcess;
import com.kindlepocket.cms.service.GridFSService;
import com.kindlepocket.cms.service.MailService;
import com.kindlepocket.cms.service.SearchRecordRepository;
import com.kindlepocket.cms.service.SubscriberRepository;
import com.kindlepocket.cms.utils.Constants;
import com.kindlepocket.cms.utils.DateFormatUtils;

@Controller
@RequestMapping("/bookManage")
public class BookManagementController {

	private static Logger logger = Logger.getLogger(BookManagementController.class);

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private SubscriberRepository ssbRepository;

	@Autowired
	private MailService mailService;

	@Autowired
	private GridFSService gridFSService;

	@Autowired
	private FileProcess fileProcess;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private SearchRecordRepository srRepository;
	

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@RequestMapping("/insert")
	public void testInsert() {
		System.out.println("inserting............");
		List<TextBook> books = new ArrayList<TextBook>();
		for (int i = 0; i < 500; i++) {
			TextBook book = new TextBook();
			book.setId(i + "");
			book.setAuthor("nasuf_" + i);
			book.setTitle("ephemeris_No." + i);
			book.setUploadDate(new Date());
			book.setDownloadTimes(0L);
			book.setKindleMailTimes(0L);
			book.setUploaderName("nasuf");
			book.setSize(0L);
			book.setFormat("mobi");
			books.add(book);
		}
		this.bookRepository.insert(books);
	}

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> findAll(@RequestParam(value = "key") String key,
			@RequestParam(value = "value") String value,
			@RequestParam(value = "subscriberOpenId") String subscriberOpenId) {
		List<TextBook> queryResult = new ArrayList<TextBook>();
		List<TextBook> booksQueriedByTitle = new ArrayList<TextBook>();
		List<TextBook> booksQueriedByAuthor = new ArrayList<TextBook>();
		if (logger.isInfoEnabled()) {
			logger.info("query key: " + key + " query value: " + value);
		}
		
		SearchRecord sr = new SearchRecord();
		sr.setContent(value);
		sr.setSubscriberOpenId(subscriberOpenId);
		sr.setSearchDate(new Date());
		this.srRepository.insert(sr);
		
		if (key.toString().equals("title")) {
			// books = this.bookRepository.findByTitleLike(value);
			booksQueriedByTitle = this.bookRepository.findByTitleLikeIgnoreCase(value);
			booksQueriedByAuthor = this.bookRepository.findByAuthorLikeIgnoreCase(value);
			queryResult.addAll(booksQueriedByTitle);
			queryResult.addAll(booksQueriedByAuthor);
			if (logger.isInfoEnabled()) {
				logger.info("query results size: [" + queryResult.size() + "]");
			}
			try {
				return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(queryResult));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else if (key.toString().equals("id")) {
			TextBook book = this.bookRepository.findById(value);
			queryResult.add(book);
			if (logger.isInfoEnabled()) {
				logger.info("query result: " + queryResult.toString());
			}
			try {
				return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(queryResult));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	@RequestMapping(value = "/saveAll", method = RequestMethod.GET)
	public void saveBooks() {

		if (logger.isInfoEnabled()) {
			logger.info("\n saving book ");
		}
		Long startTime = new Date().getTime();
		new Thread(this.fileProcess).start();
		Long endTime = new Date().getTime();
	}

	@RequestMapping("/revome")
	public void testRemove() {
		System.out.println("removing...");
		this.bookRepository.deleteAll();
		System.out.println("removment finished!");
	}

	@RequestMapping(value = "/sendBookComment", method = RequestMethod.POST)
	public ResponseEntity<String> saveBookComment(@RequestParam("bookId") String bookId,
			@RequestParam("subscriberOpenId") String subscriberOpenId, @RequestParam("content") String content) {
		Comment comment = new Comment();
		comment.setBookId(bookId);
		comment.setCreatedDate(new Date());
		comment.setSubscriberOpenId(subscriberOpenId);
		comment.setContent(content);
		String title = this.bookRepository.findById(bookId).getTitle();
		comment.setTitle(title);
		this.commentRepository.insert(comment);
		if(logger.isInfoEnabled()) {
			logger.info("Save comment: " + comment);
		}
		try {
			return ResponseEntity.status(HttpStatus.OK).body(MAPPER.writeValueAsString(comment));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping("/testFind")
	public void testFind() {
		System.out.println(this.bookRepository.findByTitleLike("ephemeris"));
		// System.out.println(this.bookRepository.findById(100L));
	}

	@RequestMapping("/toUpload")
	public String toUpload() {
		if (logger.isInfoEnabled()) {
			logger.info("redirecting to upload page...");
		}
		return "upload";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public String uploadFile(/*
								 * @RequestParam("file") CommonsMultipartFile
								 * file,
								 * 
								 * @RequestParam("uploaderName") String
								 * uploaderName,
								 */
			HttpServletRequest request, HttpServletResponse response) {

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		for (int i = 0; i < files.size(); ++i) {
			MultipartFile file = files.get(i);
			File uploadedFile = null;
			// this.gridFSService.saveFiles(file);

			String fileOriginalName = file.getOriginalFilename();
			// file name example: 0_201601211430_temp.zip
			String uploadFileName = DateFormatUtils.parseDateTimeToString(new Date().getTime()) + Constants.UNDER_LINE
					+ fileOriginalName;
			if (!file.isEmpty()) {
				try {
					File parentFilePath = new File(
							Constants.UN_SAVED_PATH + DateFormatUtils.parseDateToString(new Date().getTime()));
					if (!parentFilePath.exists()) {
						parentFilePath.mkdirs();
					}
					byte[] bytes = file.getBytes();
					uploadedFile = new File(parentFilePath + Constants.SLASH + uploadFileName);
					uploadedFile.setWritable(true, true);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
					stream.write(bytes);
					stream.close();

					// save file to the gridFS and collection
					// this.gridFSService.saveFiles();
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error("file upload failed!", e);
					}
				}
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn("no file ready to upload!");
				}
			}
		}
		return "upload successful";
	}
}

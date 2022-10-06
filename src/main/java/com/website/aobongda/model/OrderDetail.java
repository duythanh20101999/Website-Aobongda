//package com.website.aobongda.model;
//
//import java.sql.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@RequiredArgsConstructor
//@Entity
//@Table(name = "order_detail")
//public class OrderDetail extends BaseModel{
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_order")
//	private Order order;
//	
//	@NotNull
//	private Long quantity;
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_book")
//	private Book book;
//
//}

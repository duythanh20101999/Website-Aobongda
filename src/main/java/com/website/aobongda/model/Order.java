package com.website.aobongda.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseModel {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", nullable = true)
	private User user;
	
	@NotNull
	private Long totalPrice;
	
	@Column(name = "date")
	private Date date;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_payment")
	private PaymentMethod paymentMethod;
	
	@NotNull
	private int status;
	
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;
	

}

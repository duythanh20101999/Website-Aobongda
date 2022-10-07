package com.website.aobongda.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String note;
	@Column(nullable = false)
	private Long totalPriceOrigin;
	@Column(nullable = false)
	private Long priceOff;
	@Column(nullable = false)
	private Long priceShip;
	@Column(nullable = false)
	private Long totalPrice;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private String voucher;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user", nullable = true)
	private User user;

	@OneToOne
	@JoinColumn(name = "id_payment")
	private Payment payment;

	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetails;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<Voucher> vouchers;

}

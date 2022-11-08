package com.website.aobongda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.ResponseDTO;
import com.website.aobongda.dto.VoucherReq;
import com.website.aobongda.service.impl.IVoucherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
//@SecurityRequirement(name = "AUTHORIZATION")
public class VoucherController {
	private final IVoucherService ivoucherService;

	@PostMapping("/voucher")
	public ResponseEntity<?> save(@RequestBody VoucherReq voucherReq) {
		return ResponseEntity.ok(new ResponseDTO(true, "success", ivoucherService.save(voucherReq)));
	}

	@GetMapping("/voucher")
	public ResponseEntity<?> get(@RequestParam String code) {
		return ResponseEntity.ok(new ResponseDTO(true, "success", ivoucherService.get(code)));
	}

	@GetMapping("/voucher-order")
	public ResponseEntity<?> getByOrder(@RequestParam Long orderID) {
		return ResponseEntity.ok(new ResponseDTO(true, "success", ivoucherService.getByOrder(orderID)));
	}

	@PutMapping("/voucher")
	public ResponseEntity<?> update(@RequestBody VoucherReq voucherReq) {
		return ResponseEntity.ok(new ResponseDTO(true, "Success", ivoucherService.update(voucherReq)));
	}

	@DeleteMapping("/voucher")
	public ResponseEntity<?> delete(@RequestParam String code) {
		ivoucherService.delete(code);
		return ResponseEntity.ok(new ResponseDTO(true, "Success", null));
	}
    @PutMapping("/voucher-addOrder")
    private ResponseEntity<?>  addOrderToVoucher(@RequestParam Long voucherId, @RequestParam Long orderId){
        return ResponseEntity.ok(new ResponseDTO(true,"Success",ivoucherService.addOrderToVoucher(voucherId, orderId)));

    }
}
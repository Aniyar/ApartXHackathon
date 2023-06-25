package com.hackathon.apartxhackathon.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="order_response")
public class OrderCleanerResponse {
	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Order order;

	@ManyToOne
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Cleaner cleaner;

	private Integer offeredPrice;
}


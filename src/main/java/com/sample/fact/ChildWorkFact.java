package com.sample.fact;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildWorkFact {
	private int id;
	private int amount;
	private int minDate;
	private int minNumber;
}

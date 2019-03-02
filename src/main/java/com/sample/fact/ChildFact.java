package com.sample.fact;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildFact {
	private int id;
	private int amount;
	private List<Contract> contractList = new ArrayList<Contract>();
}

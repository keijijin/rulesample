package com.sample.fact;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RootFact {
	private List<ChildFact> childFactList = new ArrayList<ChildFact>();
	private List<ChildWorkFact> childWorkFactList = new ArrayList<ChildWorkFact>();
	private List<ChildWorkFact> childSortFactList = new ArrayList<ChildWorkFact>();
}

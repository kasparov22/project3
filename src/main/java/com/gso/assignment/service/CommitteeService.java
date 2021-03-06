package com.gso.assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gso.assignment.domain.Committee;
import com.gso.assignment.repository.CommitteeRepository;

@Service
@Transactional
public class CommitteeService {

	private CommitteeRepository committeeRepository;
	
	public CommitteeService(CommitteeRepository committeeRepository) {
		this.committeeRepository = committeeRepository;
	}
	
	public List<Committee> getParentCommittees() {
		List<Committee> committees = committeeRepository.findByParentIsNull();		
		return createCommitteeGraph(committees);
	}
	
	private List<Committee> createCommitteeGraph(List<Committee> committees) {
		for(Committee committee: committees) {
			List<Committee> children = committeeRepository.findByPcode(committee.getCode());
			committee.setChildren(children);
			if(!children.isEmpty()) {
				createCommitteeGraph(children);
			}
		}
		
		return committees;
	}
}

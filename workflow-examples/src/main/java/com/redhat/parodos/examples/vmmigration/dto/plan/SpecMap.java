package com.redhat.parodos.examples.vmmigration.dto.plan;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SpecMap {

	private Network network;

	private Network storage;

}

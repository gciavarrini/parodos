package com.redhat.parodos.examples.vmmigration.dto.migration;

import com.redhat.parodos.examples.vmmigration.dto.common.Metadata;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Migration {

	private String apiVersion;

	private String kind;

	private Metadata metadata;

	private Spec spec;

	private Status status;

}

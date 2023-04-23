/*
 * Parodos Workflow Service API
 * This is the API documentation for the Parodos Workflow Service. It provides operations to execute assessments to determine infrastructure options (tooling + environments). Also executes infrastructure task workflows to call downstream systems to stand-up an infrastructure option.
 *
 * The version of the OpenAPI document: v1.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.redhat.parodos.sdk.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.redhat.parodos.sdk.model.WorkFlowOption;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * WorkFlowOptionsResponseDTO
 */
@lombok.Data
@lombok.AllArgsConstructor
@lombok.Builder
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class WorkFlowOptionsResponseDTO {

	public static final String SERIALIZED_NAME_CONTINUATION_OPTIONS = "continuationOptions";

	@SerializedName(SERIALIZED_NAME_CONTINUATION_OPTIONS)
	private List<WorkFlowOption> continuationOptions = null;

	public static final String SERIALIZED_NAME_CURRENT_VERSION = "currentVersion";

	@SerializedName(SERIALIZED_NAME_CURRENT_VERSION)
	private WorkFlowOption currentVersion;

	public static final String SERIALIZED_NAME_MIGRATION_OPTIONS = "migrationOptions";

	@SerializedName(SERIALIZED_NAME_MIGRATION_OPTIONS)
	private List<WorkFlowOption> migrationOptions = null;

	public static final String SERIALIZED_NAME_NEW_OPTIONS = "newOptions";

	@SerializedName(SERIALIZED_NAME_NEW_OPTIONS)
	private List<WorkFlowOption> newOptions = null;

	public static final String SERIALIZED_NAME_OTHER_OPTIONS = "otherOptions";

	@SerializedName(SERIALIZED_NAME_OTHER_OPTIONS)
	private List<WorkFlowOption> otherOptions = null;

	public static final String SERIALIZED_NAME_UPGRADE_OPTIONS = "upgradeOptions";

	@SerializedName(SERIALIZED_NAME_UPGRADE_OPTIONS)
	private List<WorkFlowOption> upgradeOptions = null;

	public WorkFlowOptionsResponseDTO() {
	}

	public WorkFlowOptionsResponseDTO continuationOptions(List<WorkFlowOption> continuationOptions) {

		this.continuationOptions = continuationOptions;
		return this;
	}

	public WorkFlowOptionsResponseDTO addContinuationOptionsItem(WorkFlowOption continuationOptionsItem) {
		if (this.continuationOptions == null) {
			this.continuationOptions = new ArrayList<WorkFlowOption>();
		}
		this.continuationOptions.add(continuationOptionsItem);
		return this;
	}

	/**
	 * Get continuationOptions
	 * @return continuationOptions
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "")

	public List<WorkFlowOption> getContinuationOptions() {
		return continuationOptions;
	}

	public void setContinuationOptions(List<WorkFlowOption> continuationOptions) {
		this.continuationOptions = continuationOptions;
	}

	public WorkFlowOptionsResponseDTO currentVersion(WorkFlowOption currentVersion) {

		this.currentVersion = currentVersion;
		return this;
	}

	/**
	 * Get currentVersion
	 * @return currentVersion
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "")

	public WorkFlowOption getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(WorkFlowOption currentVersion) {
		this.currentVersion = currentVersion;
	}

	public WorkFlowOptionsResponseDTO migrationOptions(List<WorkFlowOption> migrationOptions) {

		this.migrationOptions = migrationOptions;
		return this;
	}

	public WorkFlowOptionsResponseDTO addMigrationOptionsItem(WorkFlowOption migrationOptionsItem) {
		if (this.migrationOptions == null) {
			this.migrationOptions = new ArrayList<WorkFlowOption>();
		}
		this.migrationOptions.add(migrationOptionsItem);
		return this;
	}

	/**
	 * Get migrationOptions
	 * @return migrationOptions
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "")

	public List<WorkFlowOption> getMigrationOptions() {
		return migrationOptions;
	}

	public void setMigrationOptions(List<WorkFlowOption> migrationOptions) {
		this.migrationOptions = migrationOptions;
	}

	public WorkFlowOptionsResponseDTO newOptions(List<WorkFlowOption> newOptions) {

		this.newOptions = newOptions;
		return this;
	}

	public WorkFlowOptionsResponseDTO addNewOptionsItem(WorkFlowOption newOptionsItem) {
		if (this.newOptions == null) {
			this.newOptions = new ArrayList<WorkFlowOption>();
		}
		this.newOptions.add(newOptionsItem);
		return this;
	}

	/**
	 * Get newOptions
	 * @return newOptions
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "")

	public List<WorkFlowOption> getNewOptions() {
		return newOptions;
	}

	public void setNewOptions(List<WorkFlowOption> newOptions) {
		this.newOptions = newOptions;
	}

	public WorkFlowOptionsResponseDTO otherOptions(List<WorkFlowOption> otherOptions) {

		this.otherOptions = otherOptions;
		return this;
	}

	public WorkFlowOptionsResponseDTO addOtherOptionsItem(WorkFlowOption otherOptionsItem) {
		if (this.otherOptions == null) {
			this.otherOptions = new ArrayList<WorkFlowOption>();
		}
		this.otherOptions.add(otherOptionsItem);
		return this;
	}

	/**
	 * Get otherOptions
	 * @return otherOptions
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "")

	public List<WorkFlowOption> getOtherOptions() {
		return otherOptions;
	}

	public void setOtherOptions(List<WorkFlowOption> otherOptions) {
		this.otherOptions = otherOptions;
	}

	public WorkFlowOptionsResponseDTO upgradeOptions(List<WorkFlowOption> upgradeOptions) {

		this.upgradeOptions = upgradeOptions;
		return this;
	}

	public WorkFlowOptionsResponseDTO addUpgradeOptionsItem(WorkFlowOption upgradeOptionsItem) {
		if (this.upgradeOptions == null) {
			this.upgradeOptions = new ArrayList<WorkFlowOption>();
		}
		this.upgradeOptions.add(upgradeOptionsItem);
		return this;
	}

	/**
	 * Get upgradeOptions
	 * @return upgradeOptions
	 **/
	@javax.annotation.Nullable
	@ApiModelProperty(value = "")

	public List<WorkFlowOption> getUpgradeOptions() {
		return upgradeOptions;
	}

	public void setUpgradeOptions(List<WorkFlowOption> upgradeOptions) {
		this.upgradeOptions = upgradeOptions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		WorkFlowOptionsResponseDTO workFlowOptionsResponseDTO = (WorkFlowOptionsResponseDTO) o;
		return Objects.equals(this.continuationOptions, workFlowOptionsResponseDTO.continuationOptions)
				&& Objects.equals(this.currentVersion, workFlowOptionsResponseDTO.currentVersion)
				&& Objects.equals(this.migrationOptions, workFlowOptionsResponseDTO.migrationOptions)
				&& Objects.equals(this.newOptions, workFlowOptionsResponseDTO.newOptions)
				&& Objects.equals(this.otherOptions, workFlowOptionsResponseDTO.otherOptions)
				&& Objects.equals(this.upgradeOptions, workFlowOptionsResponseDTO.upgradeOptions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(continuationOptions, currentVersion, migrationOptions, newOptions, otherOptions,
				upgradeOptions);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class WorkFlowOptionsResponseDTO {\n");
		sb.append("    continuationOptions: ").append(toIndentedString(continuationOptions)).append("\n");
		sb.append("    currentVersion: ").append(toIndentedString(currentVersion)).append("\n");
		sb.append("    migrationOptions: ").append(toIndentedString(migrationOptions)).append("\n");
		sb.append("    newOptions: ").append(toIndentedString(newOptions)).append("\n");
		sb.append("    otherOptions: ").append(toIndentedString(otherOptions)).append("\n");
		sb.append("    upgradeOptions: ").append(toIndentedString(upgradeOptions)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces (except the
	 * first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
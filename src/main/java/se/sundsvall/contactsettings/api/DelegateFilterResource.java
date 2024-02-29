package se.sundsvall.contactsettings.api;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import se.sundsvall.contactsettings.api.model.Filter;
import se.sundsvall.contactsettings.service.DelegateFilterService;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;

@RestController
@Validated
@RequestMapping("/delegates/{id}/filters")
@Tag(name = "Delegates", description = "Delegate operations")
public class DelegateFilterResource {

	private final DelegateFilterService delegateFilterService;

	public DelegateFilterResource(DelegateFilterService delegateFilterService) {
		this.delegateFilterService = delegateFilterService;
	}

	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	@Operation(summary = "Create delegate filter")
	@ApiResponse(responseCode = "201", headers = @Header(name = LOCATION, schema = @Schema(type = "string")), description = "Successful operation", useReturnTypeSchema = true)
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<Void> create(
		@Parameter(name = "id", description = "Delegate ID", example = "81471222-5798-11e9-ae24-57fa13b361e1") @ValidUuid @PathVariable(name = "id") String id,
		@NotNull @Valid @RequestBody Filter body) {

		return created(fromPath("/delegates/{id}/filters/{filterId}").buildAndExpand(id, Optional.ofNullable(delegateFilterService.create(id, body)).orElse(Filter.create()).getId()).toUri())
			.header(CONTENT_TYPE, ALL_VALUE)
			.build();
	}

	@GetMapping(path = "/{filterId}", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	@Operation(summary = "Read delegate filter")
	@ApiResponse(responseCode = "200", description = "Successful operation", useReturnTypeSchema = true)
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<Filter> read(
		@Parameter(name = "id", description = "Delegate ID", example = "81471222-5798-11e9-ae24-57fa13b361e1") @ValidUuid @PathVariable(name = "id") final String id,
		@Parameter(name = "filterId", description = "Delegate filter ID", example = "b95eb1ed-0561-49f2-a7dc-5b8bc0411778") @ValidUuid @PathVariable(name = "filterId") String filterId) {

		return ok(delegateFilterService.read(id, filterId));
	}

	@PatchMapping(path = "/{filterId}", consumes = APPLICATION_JSON_VALUE, produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	@Operation(summary = "Update delegate filter")
	@ApiResponse(responseCode = "200", description = "Successful operation", useReturnTypeSchema = true)
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<Filter> update(
		@Parameter(name = "id", description = "Delegate ID", example = "81471222-5798-11e9-ae24-57fa13b361e1") @ValidUuid @PathVariable(name = "id") String id,
		@Parameter(name = "filterId", description = "Delegate filter ID", example = "b95eb1ed-0561-49f2-a7dc-5b8bc0411778") @ValidUuid @PathVariable(name = "filterId") String filterId,
		@NotNull @Valid @RequestBody final Filter body) {

		return ok(delegateFilterService.update(id, filterId, body));
	}

	@DeleteMapping(path = "/{filterId}", produces = APPLICATION_PROBLEM_JSON_VALUE)
	@Operation(summary = "Delete delegate filter")
	@ApiResponse(responseCode = "204", description = "Successful operation", useReturnTypeSchema = true)
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<Void> delete(
		@Parameter(name = "id", description = "Delegate ID", example = "81471222-5798-11e9-ae24-57fa13b361e1") @ValidUuid @PathVariable(name = "id") String id,
		@Parameter(name = "filterId", description = "Delegate filter ID", example = "b95eb1ed-0561-49f2-a7dc-5b8bc0411778") @ValidUuid @PathVariable(name = "filterId") String filterId) {

		delegateFilterService.delete(id, filterId);
		return noContent().build();
	}
}

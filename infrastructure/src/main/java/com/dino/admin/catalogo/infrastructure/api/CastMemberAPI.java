package com.dino.admin.catalogo.infrastructure.api;

import com.dino.admin.catalogo.domain.pagination.Pagination;
import com.dino.admin.catalogo.infrastructure.castmember.models.CastMemberListResponse;
import com.dino.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;
import com.dino.admin.catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import com.dino.admin.catalogo.infrastructure.castmember.models.UpdateCastMemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("cast_members")
@Tag(name = "Cast Members")
public interface CastMemberAPI {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new CastMember")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> create(@RequestBody CreateCastMemberRequest request);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all cast members paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<CastMemberListResponse> list(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String dir
    );

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a cast member by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CastMember retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "CastMember was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    CastMemberResponse getById(@PathVariable("id") final String id);

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updated a cast member by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CastMember updated successfully"),
            @ApiResponse(responseCode = "404", description = "CastMember was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> updateById(@PathVariable("id") final String id, @RequestBody UpdateCastMemberRequest request);

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a cast member by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "CastMember deleted successfully"),
            @ApiResponse(responseCode = "404", description = "CastMember was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    void deleteById(@PathVariable("id") final String id);
}

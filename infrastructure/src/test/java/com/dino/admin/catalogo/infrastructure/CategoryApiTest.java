package com.dino.admin.catalogo.infrastructure;

import com.dino.admin.catalogo.ControllerTest;
import com.dino.admin.catalogo.application.category.create.CreateCategoryResponse;
import com.dino.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.dino.admin.catalogo.application.category.retrieve.get.CategoryResponse;
import com.dino.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.dino.admin.catalogo.domain.category.Category;
import com.dino.admin.catalogo.domain.category.CategoryId;
import com.dino.admin.catalogo.domain.exceptions.DomainException;
import com.dino.admin.catalogo.domain.validation.Error;
import com.dino.admin.catalogo.domain.validation.handler.Notification;
import com.dino.admin.catalogo.infrastructure.api.CategoryAPI;
import com.dino.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryApiTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Right(CreateCategoryResponse.from("123")));

        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string("location", "/categories/123"),
                        header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                        jsonPath("$.id").value("123")
                );

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnNotification() throws Exception {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessage = "'name' should not be null";

        final var aInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedMessage))));

        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        header().string("location", Matchers.nullValue()),
                        header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                        jsonPath("$.errors", hasSize(1)),
                        jsonPath("$.errors[0].message", equalTo(expectedMessage))
                );

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAInvalidCommand_whenCallsCreateCategory_thenShouldReturnDomainException() throws Exception {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessage = "'name' should not be null";

        final var aInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedMessage)));

        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        header().string("location", Matchers.nullValue()),
                        header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                        jsonPath("$.errors", hasSize(1)),
                        jsonPath("$.errors[0].message", equalTo(expectedMessage)),
                        jsonPath("$.message", equalTo(expectedMessage))
                );

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAValidId_whenCallGetCategory_shouldReturnCategoryId() throws Exception {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId().getValue();

        when(getCategoryByIdUseCase.execute(any()))
                .thenReturn(CategoryResponse.from(aCategory));

        final var request = get("/categories/{id}", expectedId);

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", equalTo(expectedId)),
                        jsonPath("$.name", equalTo(expectedName)),
                        jsonPath("$.description", equalTo(expectedDescription)),
                        jsonPath("$.is_active", equalTo(expectedIsActive)),
                        jsonPath("$.created_at", equalTo(aCategory.getCreatedAt().toString())),
                        jsonPath("$.updated_at", equalTo(aCategory.getUpdatedAt().toString())),
                        jsonPath("$.deleted_at", equalTo(aCategory.getDeletedAt()))

                );

        verify(getCategoryByIdUseCase, times(1)).execute(eq(expectedId));
    }

    @Test
    public void givenAInvalidId_whenCallGetCategory_shouldReturnNotFound() throws Exception {
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedId = CategoryId.from("123").getValue();

        when(getCategoryByIdUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedErrorMessage)));

        final var request = get("/categories/{id}", expectedId);

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }
}

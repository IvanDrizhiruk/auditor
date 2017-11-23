package com.auditor.web.rest;

import com.auditor.AuditorApp;

import com.auditor.domain.Inspection;
import com.auditor.repository.InspectionRepository;
import com.auditor.service.InspectionService;
import com.auditor.service.dto.InspectionDTO;
import com.auditor.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.auditor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InspectionResource REST controller.
 *
 * @see InspectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuditorApp.class)
public class InspectionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restInspectionMockMvc;

    private Inspection inspection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InspectionResource inspectionResource = new InspectionResource(inspectionService);
        this.restInspectionMockMvc = MockMvcBuilders.standaloneSetup(inspectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspection createEntity() {
        Inspection inspection = new Inspection();
        inspection.setName(DEFAULT_NAME);
        inspection.setDescription(DEFAULT_DESCRIPTION);
        inspection .setStartDate(DEFAULT_START_DATE);
        inspection.setEndDate(DEFAULT_END_DATE);

        return inspection;
    }

    @Before
    public void initTest() {
        inspectionRepository.deleteAll();
        inspection = createEntity();
    }

    @Test
    public void createInspection() throws Exception {
        int databaseSizeBeforeCreate = inspectionRepository.findAll().size();

        // Create the Inspection
        InspectionDTO inspectionDTO = modelMapper.map(inspection, InspectionDTO.class);
        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeCreate + 1);
        Inspection testInspection = inspectionList.get(inspectionList.size() - 1);
        assertThat(testInspection.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInspection.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInspection.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testInspection.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    public void createInspectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inspectionRepository.findAll().size();

        // Create the Inspection with an existing ID
        inspection.setId("existing_id");
        InspectionDTO inspectionDTO = modelMapper.map(inspection, InspectionDTO.class);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setName(null);

        // Create the Inspection, which fails.
        InspectionDTO inspectionDTO = modelMapper.map(inspection, InspectionDTO.class);

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setDescription(null);

        // Create the Inspection, which fails.
        InspectionDTO inspectionDTO = modelMapper.map(inspection, InspectionDTO.class);

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setStartDate(null);

        // Create the Inspection, which fails.
        InspectionDTO inspectionDTO = modelMapper.map(inspection, InspectionDTO.class);

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setEndDate(null);

        // Create the Inspection, which fails.
        InspectionDTO inspectionDTO = modelMapper.map(inspection, InspectionDTO.class);

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllInspections() throws Exception {
        // Initialize the database
        inspectionRepository.save(inspection);

        // Get all the inspectionList
        restInspectionMockMvc.perform(get("/api/inspections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspection.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    public void getInspection() throws Exception {
        // Initialize the database
        inspectionRepository.save(inspection);

        // Get the inspection
        restInspectionMockMvc.perform(get("/api/inspections/{id}", inspection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inspection.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    public void getNonExistingInspection() throws Exception {
        // Get the inspection
        restInspectionMockMvc.perform(get("/api/inspections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInspection() throws Exception {
        // Initialize the database
        inspectionRepository.save(inspection);
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();

        // Update the inspection
        Inspection updatedInspection = inspectionRepository.findOne(inspection.getId());
        updatedInspection.setName(UPDATED_NAME);
        updatedInspection.setDescription(UPDATED_DESCRIPTION);
        updatedInspection.setStartDate(UPDATED_START_DATE);
        updatedInspection.setEndDate(UPDATED_END_DATE);
        InspectionDTO inspectionDTO = modelMapper.map(updatedInspection, InspectionDTO.class);

        restInspectionMockMvc.perform(put("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isOk());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
        Inspection testInspection = inspectionList.get(inspectionList.size() - 1);
        assertThat(testInspection.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInspection.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInspection.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testInspection.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    public void updateNonExistingInspection() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();

        // Create the Inspection
        InspectionDTO inspectionDTO = modelMapper.map(inspection, InspectionDTO.class);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInspectionMockMvc.perform(put("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteInspection() throws Exception {
        // Initialize the database
        inspectionRepository.save(inspection);
        int databaseSizeBeforeDelete = inspectionRepository.findAll().size();

        // Get the inspection
        restInspectionMockMvc.perform(delete("/api/inspections/{id}", inspection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspection.class);
        Inspection inspection1 = new Inspection();
        inspection1.setId("id1");
        Inspection inspection2 = new Inspection();
        inspection2.setId(inspection1.getId());
        assertThat(inspection1).isEqualTo(inspection2);
        inspection2.setId("id2");
        assertThat(inspection1).isNotEqualTo(inspection2);
        inspection1.setId(null);
        assertThat(inspection1).isNotEqualTo(inspection2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionDTO.class);
        InspectionDTO inspectionDTO1 = new InspectionDTO();
        inspectionDTO1.setId("id1");
        InspectionDTO inspectionDTO2 = new InspectionDTO();
        assertThat(inspectionDTO1).isNotEqualTo(inspectionDTO2);
        inspectionDTO2.setId(inspectionDTO1.getId());
        assertThat(inspectionDTO1).isEqualTo(inspectionDTO2);
        inspectionDTO2.setId("id2");
        assertThat(inspectionDTO1).isNotEqualTo(inspectionDTO2);
        inspectionDTO1.setId(null);
        assertThat(inspectionDTO1).isNotEqualTo(inspectionDTO2);
    }
}

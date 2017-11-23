package com.auditor.web.rest;

import com.auditor.AuditorApp;

import com.auditor.domain.Items;
import com.auditor.repository.ItemsRepository;
import com.auditor.service.ItemsService;
import com.auditor.service.dto.ItemsDTO;
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

import java.util.List;

import static com.auditor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ItemsResource REST controller.
 *
 * @see ItemsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuditorApp.class)
public class ItemsResourceIntTest {

    private static final String DEFAULT_INSPECTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_INSPECTION_ID = "BBBBBBBBBB";

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restItemsMockMvc;

    private Items items;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemsResource itemsResource = new ItemsResource(itemsService);
        this.restItemsMockMvc = MockMvcBuilders.standaloneSetup(itemsResource)
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
    public static Items createEntity() {
        Items items = new Items();
        items.setInspectionId(DEFAULT_INSPECTION_ID);
        return items;
    }

    @Before
    public void initTest() {
        itemsRepository.deleteAll();
        items = createEntity();
    }

    @Test
    public void createItems() throws Exception {
        int databaseSizeBeforeCreate = itemsRepository.findAll().size();

        // Create the Items
        ItemsDTO itemsDTO = modelMapper.map(items, ItemsDTO.class);
        restItemsMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemsDTO)))
            .andExpect(status().isCreated());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeCreate + 1);
        Items testItems = itemsList.get(itemsList.size() - 1);
        assertThat(testItems.getInspectionId()).isEqualTo(DEFAULT_INSPECTION_ID);
    }

    @Test
    public void createItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemsRepository.findAll().size();

        // Create the Items with an existing ID
        items.setId("existing_id");
        ItemsDTO itemsDTO = modelMapper.map(items, ItemsDTO.class);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemsMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkInspectionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemsRepository.findAll().size();
        // set the field null
        items.setInspectionId(null);

        // Create the Items, which fails.
        ItemsDTO itemsDTO = modelMapper.map(items, ItemsDTO.class);

        restItemsMockMvc.perform(post("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemsDTO)))
            .andExpect(status().isBadRequest());

        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllItems() throws Exception {
        // Initialize the database
        itemsRepository.save(items);

        // Get all the itemsList
        restItemsMockMvc.perform(get("/api/items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(items.getId())))
            .andExpect(jsonPath("$.[*].inspectionId").value(hasItem(DEFAULT_INSPECTION_ID.toString())));
    }

    @Test
    public void getItems() throws Exception {
        // Initialize the database
        itemsRepository.save(items);

        // Get the items
        restItemsMockMvc.perform(get("/api/items/{id}", items.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(items.getId()))
            .andExpect(jsonPath("$.inspectionId").value(DEFAULT_INSPECTION_ID.toString()));
    }

    @Test
    public void getNonExistingItems() throws Exception {
        // Get the items
        restItemsMockMvc.perform(get("/api/items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateItems() throws Exception {
        // Initialize the database
        itemsRepository.save(items);
        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();

        // Update the items
        Items updatedItems = itemsRepository.findOne(items.getId());
        updatedItems.setInspectionId(UPDATED_INSPECTION_ID);
        ItemsDTO itemsDTO = modelMapper.map(updatedItems, ItemsDTO.class);

        restItemsMockMvc.perform(put("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemsDTO)))
            .andExpect(status().isOk());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate);
        Items testItems = itemsList.get(itemsList.size() - 1);
        assertThat(testItems.getInspectionId()).isEqualTo(UPDATED_INSPECTION_ID);
    }

    @Test
    public void updateNonExistingItems() throws Exception {
        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();

        // Create the Items
        ItemsDTO itemsDTO = modelMapper.map(items, ItemsDTO.class);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restItemsMockMvc.perform(put("/api/items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemsDTO)))
            .andExpect(status().isCreated());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteItems() throws Exception {
        // Initialize the database
        itemsRepository.save(items);
        int databaseSizeBeforeDelete = itemsRepository.findAll().size();

        // Get the items
        restItemsMockMvc.perform(delete("/api/items/{id}", items.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Items.class);
        Items items1 = new Items();
        items1.setId("id1");
        Items items2 = new Items();
        items2.setId(items1.getId());
        assertThat(items1).isEqualTo(items2);
        items2.setId("id2");
        assertThat(items1).isNotEqualTo(items2);
        items1.setId(null);
        assertThat(items1).isNotEqualTo(items2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemsDTO.class);
        ItemsDTO itemsDTO1 = new ItemsDTO();
        itemsDTO1.setId("id1");
        ItemsDTO itemsDTO2 = new ItemsDTO();
        assertThat(itemsDTO1).isNotEqualTo(itemsDTO2);
        itemsDTO2.setId(itemsDTO1.getId());
        assertThat(itemsDTO1).isEqualTo(itemsDTO2);
        itemsDTO2.setId("id2");
        assertThat(itemsDTO1).isNotEqualTo(itemsDTO2);
        itemsDTO1.setId(null);
        assertThat(itemsDTO1).isNotEqualTo(itemsDTO2);
    }
}

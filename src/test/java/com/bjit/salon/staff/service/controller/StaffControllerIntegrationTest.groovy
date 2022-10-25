package com.bjit.salon.staff.service.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class StaffControllerIntegrationTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    def "should create a new staff"(){
        given:
        def content = "{\n" +
                "    \"address\":\"Gopalgonj22\",\n" +
                "    \"salonId\":1,\n" +
                "    \"userId\":4,\n" +
                "    \"contractNumber\":\"45654656\",\n" +
                "    \"salary\":\"35000\",\n" +
                "    \"employeementDate\":\"\",\n" +
                "    \"employeementType\":\"FULL_TIME\"\n" +
                "}"

        expect:
        mockMvc.perform(post("/api/v1/staffs")
                .content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is2xxSuccessful());

    }

    def "should update an existing staff"(){
        given:
        def content = "{\n" +
                "    \"id\":1,\n" +
                "    \"address\":\"Gopalgonj\",\n" +
                "    \"salonId\":1,\n" +
                "    \"userId\":3,\n" +
                "    \"contractNumber\":\"111156\",\n" +
                "    \"salary\":\"40000\",\n" +
                "    \"employeementDate\":\"\",\n" +
                "    \"employeementType\":\"FULL_TIME\"\n" +
                "}"

        expect:
        mockMvc.perform(put("/api/v1/staffs")
                .content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is2xxSuccessful())

    }

    def "should return the list of staffs"(){

        expect:
        mockMvc.perform(get("/api/v1/staffs"))
                .andExpect(status().isOk())
                .andDo(print());


    }

    def "should return a staff object by staff id"(){

        expect:
        mockMvc.perform(get("/api/v1/staffs/1"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    def "should update the status available"(){

        expect:
        mockMvc.perform(get("/api/v1/staffs/1/status"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    def "should update the status un-available"(){

        expect:
        mockMvc.perform(get("/api/v1/staffs/1/status"))
                .andExpect(status().isOk())
                .andDo(print())
    }

    def "should return all the staffs of a salon by salon id"(){
        expect:
        mockMvc.perform(get("/api/v1/salons/1/staffs"))
                .andExpect(status().isOk())
                .andDo(print())
    }

    def "should return all the available staffs of a salon by salon id"(){
        expect:
        mockMvc.perform(get("/api/v1/salons/1/available/staffs"))
                .andExpect(status().isOk())
                .andDo(print())
    }

}
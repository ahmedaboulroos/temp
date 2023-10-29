package com.example.stc.controller;

import com.example.stc.database.entity.Folder;
import com.example.stc.service.space.SpaceService;
import com.example.stc.service.space.dto.CreateSpaceRequest;
import com.example.stc.service.space.dto.CreateSpaceResponse;
import com.example.stc.service.space.dto.GetSpaceResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SpaceController {

    private final SpaceService spaceService;

    public SpaceController(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @GetMapping("/spaces")
    public List<GetSpaceResponse> getAllSpaces() {
        return spaceService.getAllSpaces();
    }

    @GetMapping("/spaces/{id}")
    public GetSpaceResponse getSpace(@PathVariable Long id) {
        return spaceService.getSpaceById(id);
    }

    @PostMapping("/spaces")
    public CreateSpaceResponse addNewSpace(@RequestBody CreateSpaceRequest request) {
        return spaceService.createSpace(request);
    }

}

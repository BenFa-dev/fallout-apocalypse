package com.apocalypse.thefall.service.skill;

import com.apocalypse.thefall.entity.character.skill.Skill;
import com.apocalypse.thefall.repository.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        return skillRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
    }

}
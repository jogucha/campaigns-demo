package uax.madm.devops.campaigns_demo.infrastructure.dto;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import uax.madm.devops.campaigns_demo.domain.model.Campaign;

public record CampaignDto( // @formatter:off

                           Long id,

                           @NotBlank(message = "code can't be null")
                           String code,

                           @NotBlank(groups = { CampaignUpdateValidationData.class, Default.class },
                                   message = "name can't be blank")
                           String name,

                           @Size(groups = { CampaignUpdateValidationData.class, Default.class },
                                   min = 10, max = 50,
                                   message = "description must be between 10 and 50 characters long")
                           String description,

                           Set<TaskDto> tasks){ // @formatter:on


    public static class Mapper {
        public static Campaign toModel(CampaignDto dto) {
            if (dto == null)
                return null;

            Campaign model = new Campaign(dto.code(), dto.name(), dto.description());
            if (!Objects.isNull(dto.id())) {
                model.setId(dto.id());
            }

            // we won't allow creating or modifying Tasks via the Campaign
            return model;
        }

        public static CampaignDto toDto(Campaign model) {
            if (model == null)
                return null;
            Set<TaskDto> tasks = model.getTasks()
                    .stream()
                    .map(TaskDto.Mapper::toDto)
                    .collect(Collectors.toSet());
            CampaignDto dto = new CampaignDto(model.getId(), model.getCode(), model.getName(), model.getDescription(), tasks);
            return dto;
        }
    }

    public interface CampaignUpdateValidationData {
    }

}
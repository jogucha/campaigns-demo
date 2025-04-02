package uax.madm.devops.campaigns_demo.infrastructure.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uax.madm.devops.campaigns_demo.domain.model.ServiceUser;

public record UserDto( // @formatter:off
                       @NotNull(groups = { TaskDto.TaskUpdateValidationData.class, TaskDto.TaskUpdateAssigneeValidationData.class },
                               message = "User id can't be null")
                       Long id,

                       @NotBlank(message = "email can't be blank")
                       @Email(message = "You must provide a valid email address")
                       String email

) { // @formatter:on

    public static class Mapper {
        public static ServiceUser toModel(UserDto dto) {
            if (dto == null)
                return null;
            ServiceUser model = new ServiceUser(dto.email());
            // if (!Objects.isNull(dto.id())) {
            model.setId(dto.id());
            // }

            return model;
        }

        public static UserDto toDto(ServiceUser model) {
            if (model == null)
                return null;
            UserDto dto = new UserDto(model.getId(), model.getEmail());
            return dto;
        }
    }

    public interface WorkerUpdateValidationData {
    }

}

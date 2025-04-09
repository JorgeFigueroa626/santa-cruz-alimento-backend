package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.business.BusinessRequestDto;
import santa_cruz_alimento_backend.dto.response.business.BusinessResponseDto;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IBusinessService {

    BusinessResponseDto save(BusinessRequestDto business) throws ExceptionNotFoundException;

    Business getById(Long id) throws ExceptionNotFoundException;

    List<BusinessResponseDto> findAll() throws ExceptionNotFoundException;

    BusinessResponseDto updateById(Long id, BusinessRequestDto business) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}

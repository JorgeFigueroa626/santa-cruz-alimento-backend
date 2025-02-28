package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IBusinessService {

    Business save(Business business) throws ExceptionNotFoundException;

    Business getById(Long id) throws ExceptionNotFoundException;

    List<Business> findAll() throws ExceptionNotFoundException;

    Business updateById(Long id, Business business) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}

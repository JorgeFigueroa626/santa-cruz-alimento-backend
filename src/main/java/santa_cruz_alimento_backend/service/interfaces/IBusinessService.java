package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Business;

import java.util.List;

@Service
public interface IBusinessService {

    Business save(Business business);

    Business getById(Long id);

    List<Business> findAll();

    Business updateById(Long id, Business business);

    void deleteById(Long id);
}

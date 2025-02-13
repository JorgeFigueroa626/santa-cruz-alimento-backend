package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.repository.IBusinessRepository;
import santa_cruz_alimento_backend.service.interfaces.IBusinessService;

import java.util.List;

@Service
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    private IBusinessRepository businessRepository;

    @Override
    public Business save(Business business) {
        return businessRepository.save(business);
    }

    @Override
    public Business getById(Long id) {
        return businessRepository.findById(id).orElseThrow(() -> new RuntimeException("Negocio no encontrado con id: " + id));
    }

    @Override
    public List<Business> findAll() {
        return businessRepository.findAll();
    }

    @Override
    public Business updateById(Long id, Business business) {
        Business businessId = businessRepository.findById(id).orElseThrow(() -> new RuntimeException("Negocio no encontrado con id: " + id));
        businessId.setName(business.getName());
        return businessRepository.save(businessId);
    }

    @Override
    public void deleteById(Long id) {
        businessRepository.deleteById(id);
    }
}

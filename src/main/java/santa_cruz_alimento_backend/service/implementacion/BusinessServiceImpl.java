package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.BusinessRequestDto;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IBusinessRepository;
import santa_cruz_alimento_backend.service.interfaces.IBusinessService;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

import java.util.List;

@Service
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    private IBusinessRepository businessRepository;

    @Override
    public Business save(BusinessRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            Business business = new Business();
            business.setName(requestDto.getName());
            business.setStatus(ReplyStatus.ACTIVE.getValue());
            return businessRepository.save(business);
        }catch ( Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Business getById(Long id) throws ExceptionNotFoundException {
        try {
            return businessRepository.findById(id).orElseThrow(() -> new RuntimeException("Negocio no encontrado con id: " + id));

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public List<Business> findAll() throws ExceptionNotFoundException {
        try {
            return businessRepository.findAll();
        } catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public Business updateById(Long id, BusinessRequestDto business) throws ExceptionNotFoundException{
        try {
            Business businessId = businessRepository.findById(id).orElseThrow(() -> new RuntimeException("Negocio no encontrado con id: " + id));
            businessId.setName(business.getName());
            businessId.setStatus(business.getStatus());
            return businessRepository.save(businessId);

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            Business businessId = businessRepository.findById(id).orElseThrow(() -> new RuntimeException("Negocio no encontrado con id: " + id));
            businessId.setStatus(ReplyStatus.INACTIVE.getValue());
            businessRepository.save(businessId);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}

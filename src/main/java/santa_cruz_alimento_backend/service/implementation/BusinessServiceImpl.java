package santa_cruz_alimento_backend.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.BusinessRequestDto;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IBusinessRepository;
import santa_cruz_alimento_backend.service.interfaces.IBusinessService;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;

@Service
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    private IBusinessRepository businessRepository;

    private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);

    @Override
    public Business save(BusinessRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", requestDto);

            Business business = new Business();
            business.setName(requestDto.getName());
            business.setStatus(ReplyStatus.ACTIVE.getValue());
            Business save = businessRepository.save(business);

            logger.info("Negocio registrado : {}", save);
            return save;
        }catch ( Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Business getById(Long id) throws ExceptionNotFoundException {
        try {
            return businessRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BUSINESS_WITH_ID + id));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public List<Business> findAll() throws ExceptionNotFoundException {
        try {
            return businessRepository.findAll();
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public Business updateById(Long id, BusinessRequestDto business) throws ExceptionNotFoundException{
        try {
            logger.info("Solicitud a modificar: {}", business);

            Business businessId = businessRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BUSINESS_WITH_ID + id));
            businessId.setName(business.getName());
            businessId.setStatus(business.getStatus());
            Business update = businessRepository.save(businessId);

            logger.info("Negocio modificado: {}", update);
            return update;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            Business businessId = businessRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BUSINESS_WITH_ID + id));
            businessId.setStatus(ReplyStatus.INACTIVE.getValue());
            businessRepository.save(businessId);
            logger.info("Negocio eliminado : {}", businessId);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}

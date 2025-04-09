package santa_cruz_alimento_backend.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.business.BusinessRequestDto;
import santa_cruz_alimento_backend.dto.response.business.BusinessResponseDto;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.mapper.IBusinessMapper;
import santa_cruz_alimento_backend.repository.IBusinessRepository;
import santa_cruz_alimento_backend.service.interfaces.IBusinessService;
import santa_cruz_alimento_backend.util.constant.ReplyStatus;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;

@Service
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    private IBusinessRepository businessRepository;

    @Autowired
    private IBusinessMapper businessMapper;

    private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);

    @Transactional
    @Override
    public BusinessResponseDto save(BusinessRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", requestDto);

            Business business = businessMapper.toEntity(requestDto);
            business.setStatus(ReplyStatus.ACTIVE.getValue());
            logger.info("Negocio registrado : {}", business);
            return businessMapper.toDto(businessRepository.save(business));

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
    public List<BusinessResponseDto> findAll() throws ExceptionNotFoundException {
        try {
            return businessRepository.findAll().stream().map(businessMapper::toDto).toList();
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public BusinessResponseDto updateById(Long id, BusinessRequestDto requestDto) throws ExceptionNotFoundException{
        try {
            logger.info("Solicitud a modificar: {}", requestDto);
            return businessRepository.findById(id)
                    .map(exist ->{
                        Business business = businessMapper.toEntity(requestDto);
                        business.setId(exist.getId());
                        logger.info("Negocio modificado: {}", business);
                        return businessMapper.toDto(businessRepository.save(business));
                    }).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BUSINESS_WITH_ID + id));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
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

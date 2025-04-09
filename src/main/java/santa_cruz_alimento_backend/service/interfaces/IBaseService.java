package santa_cruz_alimento_backend.service.interfaces;


import santa_cruz_alimento_backend.dto.request.base.BaseRequestDto;
import santa_cruz_alimento_backend.dto.response.base.BaseResponseDto;
import santa_cruz_alimento_backend.entity.model.Base;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

public interface IBaseService {


    List<BaseResponseDto> ListBase() throws ExceptionNotFoundException;

    BaseResponseDto GetBaseById(Long baseId) throws ExceptionNotFoundException;

    Base registerBase(BaseRequestDto requestDto) throws ExceptionNotFoundException;

    Base updateBaseById(Long baseId, BaseRequestDto requestDto) throws ExceptionNotFoundException;

    void deleteBaseById(Long baseId) throws ExceptionNotFoundException;
}

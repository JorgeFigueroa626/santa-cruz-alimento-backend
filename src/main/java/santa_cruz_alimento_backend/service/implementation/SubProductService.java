package santa_cruz_alimento_backend.service.implementation;

/*
@Service
public class SubProductService implements ISubProductService {
    
    @Autowired
    private ISubProductRepository subProductRepository;
    
    @Autowired
    private IIngredienteRepository ingredienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(SubProduct.class);
    
    @Override
    public SubProduct save(SubProductRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            SubProduct subProduct = new SubProduct();
            subProduct.setName(requestDto.getName());
            subProduct.setStatus(ReplyStatus.ACTIVE.getValue());

            List<DetalleBase> detalleBases = requestDto.getDetallesBases().stream().map(iDto ->{
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado con el ID : " + iDto.getIngredienteId()));

                DetalleBase detalleBase = new DetalleBase();
                detalleBase.setIngrediente(ingrediente);
                detalleBase.setCantidad(iDto.getCantidad());
                detalleBase.setUnidad(iDto.getUnidad());
                detalleBase.setSubProduct(subProduct);
                return detalleBase;
            }).collect(Collectors.toList());

            subProduct.setDetalleBase(detalleBases);

            SubProduct save = subProductRepository.save(subProduct);

            logger.info("Registro base registrado: {}", save);

            return save;

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
    

    @Override
    public SubProductResponseDto getSubProductById(Long id) throws ExceptionNotFoundException {
        try {
            SubProduct subProduct = subProductRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("SubProduct no encontrado con id: " + id));

            SubProductResponseDto responseDtos = new SubProductResponseDto();
            responseDtos.setId(subProduct.getId());
            responseDtos.setName(subProduct.getName());
            responseDtos.setStatus(subProduct.getStatus());

            List<DetalleBaseResponseDto> responseDtoList = subProduct.getDetalleBase().stream().map(iDto->{
                DetalleBaseResponseDto dto = new DetalleBaseResponseDto();
                dto.setId(iDto.getId());
                dto.setIngredienteId(iDto.getIngrediente().getId());
                dto.setNombre_ingrediente(iDto.getIngrediente().getName());
                dto.setCantidad(iDto.getCantidad());
                dto.setUnidad(iDto.getUnidad());
                return dto;
            }).collect(Collectors.toList());

            responseDtos.setDetalleBases(responseDtoList);;
            return responseDtos;

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public SubProduct updateBySubProductId(Long id, SubProductRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            SubProduct subProduct = subProductRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("SubProduct no encontrado con id: " + id));

            subProduct.setName(requestDto.getName());
            subProduct.setStatus(requestDto.getStatus());

            // Lista de ingredientes actuales en la base de datos
            List<DetalleBase> detalleIngredienteActuales = subProduct.getDetalleBase();

            // Lista de IDs de ingredientes que vienen en la solicitud
            Set<Long> nuevosIds = requestDto.getDetallesBases().stream()
                    .map(DetalleBaseRequestDto::getIngredienteId)
                    .collect(Collectors.toSet());

            // **1️⃣ Eliminar ingredientes que ya no están en la lista**
            detalleIngredienteActuales.removeIf(rm->!nuevosIds.contains(rm.getIngrediente().getId()));

            // **2️⃣ Agregar o actualizar ingredientes**
            for (DetalleBaseRequestDto dto : requestDto.getDetallesBases()){
                Ingrediente ingrediente = ingredienteRepository.findById(dto.getIngredienteId())
                        .orElseThrow(() -> new ExceptionNotFoundException("subProduct no encontrado con id: " + dto.getIngredienteId()));

                // Buscar si el ingrediente ya existe en la receta
                Optional<DetalleBase> existente = detalleIngredienteActuales.stream()
                        .filter(filter->filter.getIngrediente().getId().equals(ingrediente.getId()))
                        .findFirst();

                if (existente.isPresent()) {
                    // **Actualizar cantidad y unidad si el ingrediente ya existe**
                    existente.get().setCantidad(dto.getCantidad());
                    existente.get().setUnidad(dto.getUnidad());
                }else {
                    // **Agregar nuevo ingrediente si no está en la lista**
                    DetalleBase detalleBase = new DetalleBase();
                    detalleBase.setSubProduct(subProduct);
                    detalleBase.setIngrediente(ingrediente);
                    detalleBase.setCantidad(dto.getCantidad());
                    detalleBase.setUnidad(dto.getUnidad());
                    detalleIngredienteActuales.add(detalleBase);
                }
            }

            // Guardar la receta con los ingredientes actualizados
            subProduct.setDetalleBase(detalleIngredienteActuales);
            SubProduct save = subProductRepository.save(subProduct);

            logger.info("Receta de SubProducto modificado. {}", save);
            return save;

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}*/

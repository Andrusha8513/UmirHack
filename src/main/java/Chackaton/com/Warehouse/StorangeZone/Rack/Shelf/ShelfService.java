package Chackaton.com.Warehouse.StorangeZone.Rack.Shelf;

import Chackaton.com.DTO.ShelfCreateRequest;
import Chackaton.com.Warehouse.StorangeZone.Rack.Rack;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ShelfService {
    private final ShelfRepository shelfRepository;

    public ShelfService(ShelfRepository shelfRepository){
        this.shelfRepository = shelfRepository;
    }

    public Shelf createShelf(Shelf shelf  , Rack rack){
        if (shelf.getCode() == null || shelf.getCode().trim().isEmpty()) {
            shelf.setCode(generateShelfCode(rack, shelf.getLevel(), shelf.getPosition()));
        }

        if(shelfRepository.existsByRackAndCode(rack , shelf.getCode())){ // тоже тестануть надо мб shelf.getRack() вместо rack
            throw new RuntimeException("Полка с кодом " + shelf.getCode() + "  ужу существует в этом стелаже");
        }

        validateShelfCapacity(rack, shelf);
        shelf.setRack(rack);
        return shelfRepository.save(shelf);
    }


    private String generateShelfCode(Rack rack, Integer level, Integer position) {
        // Формат: {код стеллажа}-{уровень}-{позиция} (например: ZONE-A-001-01-01)
        String baseCode = rack.getCode();

        if (level == null) {
            Integer maxLevel = shelfRepository.findMaxLevelByRack(rack);
            level = maxLevel != null ? maxLevel + 1 : 1;
        }

        if (position == null) {
            Integer maxPosition = shelfRepository.findMaxPositionByRackAndLevel(rack, level);
            position = maxPosition != null ? maxPosition + 1 : 1;
        }

        return String.format("%s-%02d-%02d", baseCode, level, position);
    }

    private void validateShelfCapacity(Rack rack, Shelf shelf) {
        if (shelf.getMaxWeight() != null && rack.getMaxWeight() != null) {
            if (shelf.getMaxWeight() > rack.getMaxWeight()) {
                throw new IllegalArgumentException(
                        String.format("Максимальный вес полки (%.2f кг) превышает возможности стеллажа (%.2f кг)",
                                shelf.getMaxWeight(), rack.getMaxWeight())
                );
            }
        }

        if (shelf.getVolume() != null) {
            Double rackVolume = calculateRackVolume(rack);
            Double usedVolume = calculateUsedRackVolume(rack);
            Double availableVolume = rackVolume - usedVolume;

            if (shelf.getVolume() > availableVolume) {
                throw new IllegalArgumentException(
                        String.format("Объем полки (%.2f м³) превышает доступный объем стеллажа (%.2f м³)",
                                shelf.getVolume(), availableVolume)
                );
            }
        }
    }

    private Double calculateRackVolume(Rack rack) {
        if (rack.getHeight() != null && rack.getWidth() != null && rack.getDepth() != null) {
            return rack.getHeight() * rack.getWidth() * rack.getDepth();
        }
        return null;
    }

    private Double calculateUsedRackVolume(Rack rack) {
        List<Shelf> shelves = shelfRepository.findByRack(rack);
        return shelves.stream()
                .map(Shelf::getVolume)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
    }




    public List<Shelf> findByRack(Long rackId) {
        return shelfRepository.findByRackId(rackId);
    }

    public Shelf updateShelf(Long shelfId, Shelf shelfDetails) {

        Shelf existingShelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new IllegalArgumentException("Не найдена полка с ID: " + shelfId));

        // Получаем стеллаж, к которому принадлежит полка (он не меняется при обновлении)
        Rack currentRack = existingShelf.getRack();


        if (!existingShelf.getCode().equals(shelfDetails.getCode())) {
            if (shelfRepository.existsByRackAndCode(currentRack, shelfDetails.getCode())) {
                throw new IllegalArgumentException(
                        "Полка с кодом '" + shelfDetails.getCode() +
                                "' уже существует в стеллаже '" + currentRack.getCode() + "'"
                );
            }
        }


        existingShelf.setCode(shelfDetails.getCode());
        existingShelf.setLevel(shelfDetails.getLevel());
        existingShelf.setPosition(shelfDetails.getPosition());
        existingShelf.setMaxWeight(shelfDetails.getMaxWeight());
        existingShelf.setVolume(shelfDetails.getVolume());
        existingShelf.setStatuses(shelfDetails.getStatuses());

        // Поле rack не должно меняться


        return shelfRepository.save(existingShelf);
    }


    @Transactional
    public List<Shelf> createShelvesInBulk(Rack rack, List<ShelfCreateRequest> shelfRequests) {
        List<Shelf> createdShelves = new ArrayList<>();

        for (ShelfCreateRequest request : shelfRequests) {
            Shelf shelf = new Shelf();
            shelf.setLevel(request.getLevel());
            shelf.setPosition(request.getPosition());
            shelf.setMaxWeight(request.getMaxWeight());
            shelf.setVolume(request.getVolume());
            shelf.setStatuses(request.getStatuses());


            shelf.setCode(generateShelfCode(rack, request.getLevel(), request.getPosition()));

            validateShelfCapacity(rack, shelf);

            shelf.setRack(rack);
            Shelf savedShelf = shelfRepository.save(shelf);
            createdShelves.add(savedShelf);
        }

        return createdShelves;
    }

}

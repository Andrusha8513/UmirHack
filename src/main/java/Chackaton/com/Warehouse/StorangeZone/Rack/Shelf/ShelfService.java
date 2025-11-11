package Chackaton.com.Warehouse.StorangeZone.Rack.Shelf;

import Chackaton.com.Warehouse.StorangeZone.Rack.Rack;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelfService {
    private final ShelfRepository shelfRepository;

    public ShelfService(ShelfRepository shelfRepository){
        this.shelfRepository = shelfRepository;
    }

    public Shelf createShelf(Shelf shelf  , Rack rack){
        if(shelfRepository.existsByRackAndCode(shelf.getRack() , shelf.getCode())){
            throw new RuntimeException("Полка с кодом " + shelf.getCode() + "  ужу существует в этом стелаже");
        }
        shelf.setRack(rack);
        return shelfRepository.save(shelf);
    }


    public List<Shelf> findByRack(Long rackId) {
        return shelfRepository.findByRackId(rackId);
    }

    public Shelf updateShelf(Long shelfId, Shelf shelfDetails) {

        Shelf existingShelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new IllegalArgumentException("Не найдена полка с ID: " + shelfId));

        // Получаем стеллаж, к которому принадлежит полка (он не меняется при обновлении)
        Rack currentRack = existingShelf.getRack();

        //  Проверка на уникальность кода полки в пределах ее стеллажа (если код изменился)
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
}

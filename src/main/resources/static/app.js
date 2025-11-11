// В разделе "6. СЦЕНАРИЙ 2: МЕНЕДЖЕР СКЛАДА (Drill-Down)"

// Формы создания (Склад, Зона, Стеллаж, Полка) - ОБНОВЛЕННЫЕ
warehouseCreateForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('warehouse-name').value;
    const address = document.getElementById('warehouse-address').value;
    const maxVolume = parseFloat(document.getElementById('warehouse-max-volume').value) || 1000;

    await apiFetch(`/warehouses/organization/${appState.currentOrgId}`, {
        method: 'POST',
        body: { name, address, maxVolume }
    });
    e.target.reset();
    loadWarehouses();
});

zoneCreateForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('zone-name').value;
    const description = document.getElementById('zone-description').value;

    // Получаем выбранные типы хранения
    const storageTypesSelect = document.getElementById('zone-storage-types');
    const storageTypes = Array.from(storageTypesSelect.selectedOptions).map(option => option.value);

    await apiFetch(`/storage-zones/${appState.currentWarehouseId}`, {
        method: 'POST',
        body: { name, description, storageTypes }
    });
    e.target.reset();
    loadZones(appState.currentWarehouseId);
});

rackCreateForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const code = document.getElementById('rack-code').value;
    const name = document.getElementById('rack-name').value;
    const rowNumber = parseInt(document.getElementById('rack-row-number').value) || null;
    const sectionNumber = parseInt(document.getElementById('rack-section-number').value) || null;
    const totalLevels = parseInt(document.getElementById('rack-total-levels').value) || null;
    const maxWeight = parseFloat(document.getElementById('rack-max-weight').value) || null;
    const height = parseFloat(document.getElementById('rack-height').value) || null;
    const width = parseFloat(document.getElementById('rack-width').value) || null;
    const depth = parseFloat(document.getElementById('rack-depth').value) || null;

    // Получаем выбранные типы стеллажей
    const typesSelect = document.getElementById('rack-types');
    const types = Array.from(typesSelect.selectedOptions).map(option => option.value);

    await apiFetch(`/racks/${appState.currentZoneId}`, {
        method: 'POST',
        body: {
            code, name, rowNumber, sectionNumber,
            totalLevels, maxWeight, height, width, depth, types
        }
    });
    e.target.reset();
    loadRacks(appState.currentZoneId);
});

shelfCreateForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const code = document.getElementById('shelf-code').value;
    const level = parseInt(document.getElementById('shelf-level').value) || null;
    const position = parseInt(document.getElementById('shelf-position').value) || null;
    const maxWeight = parseFloat(document.getElementById('shelf-max-weight').value) || null;
    const volume = parseFloat(document.getElementById('shelf-volume').value) || null;

    // Получаем выбранные статусы
    const statusesSelect = document.getElementById('shelf-statuses');
    const statuses = Array.from(statusesSelect.selectedOptions).map(option => option.value);

    await apiFetch(`/shelf/${appState.currentRackId}`, {
        method: 'POST',
        body: { code, level, position, maxWeight, volume, statuses }
    });
    e.target.reset();
    loadShelves(appState.currentRackId);
});

// Обновленная функция создания продукта
productCreateForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const body = {
        name: document.getElementById('product-name').value,
        articleNumber: document.getElementById('product-article').value,
        price: parseFloat(document.getElementById('product-price').value) || 0,
        brand: document.getElementById('product-brand').value,
        category: document.getElementById('product-category').value,
        manufacturer: document.getElementById('product-manufacturer').value,
        description: document.getElementById('product-description').value,
        weight: parseFloat(document.getElementById('product-weight').value) || null,
        length: parseFloat(document.getElementById('product-length').value) || null,
        width: parseFloat(document.getElementById('product-width').value) || null,
        height: parseFloat(document.getElementById('product-height').value) || null,
        volume: parseFloat(document.getElementById('product-volume').value) || null,
        inStock: true
    };

    try {
        await apiFetch('/products/create', { method: 'POST', body });
        alert('Продукт создан!');
        productCreateForm.reset();
        loadProductList();
    } catch (err) {
        console.error("Ошибка создания продукта:", err);
    }
});
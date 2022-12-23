INSERT INTO category_t (category_name, cipher, file_name)
VALUES ('Компьютерная техника', 'computer-technology', 'computer technology.png'),
       ('Комплектующие для ПК', 'pc-accessories', 'PC accessories.png'),
       ('Смартфоны и фототехника', 'smartphones-and-photo-equipment', 'Smartphones and photo equipment.png'),
       ('ТВ, консоли и аудио', 'tv-consoles-and-audio', 'TV, consoles and audio.png');

INSERT INTO subcategory_t (subcategory_name, cipher, file_name, category_id)
VALUES ('Ноутбуки', 'laptops', 'Laptops and accessories.jpg',
        (SELECT id FROM category_t WHERE category_name = 'Компьютерная техника')),
       ('Персональные компьютеры', 'personal-computers', 'Computers and software.jpg',
        (SELECT id FROM category_t WHERE category_name = 'Компьютерная техника')),
       ('Периферия', 'peripheralss', 'Peripherals and accessories.jpg',
        (SELECT id FROM category_t WHERE category_name = 'Компьютерная техника')),
       ('Видеокарты', 'video-cards', 'video cards.jpg',
        (SELECT id FROM category_t WHERE category_name = 'Комплектующие для ПК'));;

INSERT INTO product_t (product_name, cipher, description, present, price, purchases_number, register_date, subcategory_id)
VALUES ('14.1" Ноутбук Digma EVE 14 C411 серый', 'laptop-digma-eve-14',
        '14.1" Ноутбук Digma EVE 14 C411 серый [Full HD (1920x1080), IPS, Intel Celeron 3350, ядра: 2 х 1.1 ГГц, ' ||
        'RAM 4 ГБ, eMMC 128 ГБ, Intel HD Graphics 500 , Windows 10 Home Single Language]', TRUE, 18899, 0, '2022-12-10',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),
       ('13.3" Ноутбук Irbis NB77 черный', 'laptop-irbis-nb77',
        '13.3" Ноутбук Irbis NB77 черный [HD (1366x768), TN+film, Intel Atom Z3735F, ядра: 4 х 1.33 ГГц, RAM 2 ГБ, ' ||
        'eMMC 32 ГБ, Intel HD Graphics , Windows 10 Home Single Language]', TRUE, 14999, 0, '2022-12-12',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),
       ('14.1" Ноутбук Prestigio SmartBook 133 C4 серый', 'laptop-prestigio-smartbook-133',
        '14.1" Ноутбук Prestigio SmartBook 133 C4 серый [HD (1366x768), TN+film, AMD A4-9120e, ядра: 2 х 1.5 ГГц, ' ||
        'RAM 4 ГБ, eMMC 64 ГБ, AMD Radeon R3 , Windows 10 Pro]', TRUE, 18999, 10, '2022-12-18',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),
       ('11.6" Ноутбук ASUS Laptop 11 E210MA-GJ151T черный', 'asus-laptop-11',
        '11.6" Ноутбук ASUS Laptop 11 E210MA-GJ151T черный [HD (1366x768), TN+film, Intel Celeron N4020, ' ||
        'ядра: 2 х 1.1 ГГц, RAM 4 ГБ, eMMC 128 ГБ, Intel UHD Graphics 600 , Windows 10 Home Single Language]', TRUE,
        23999, 0, '2022-12-16', (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),
       ('14" Ноутбук Lenovo V14 IGL серый', 'laptop-lenovo-v11',
        '14" Ноутбук Lenovo V14 IGL серый [Full HD (1920x1080), TN+film, Intel Celeron N4020, ядра: 2 х 1.1 ГГц, ' ||
        'RAM 4 ГБ, SSD 128 ГБ, Intel UHD Graphics 600 , без ОС]', TRUE, 23999, 100, '2022-12-18',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),
       ('14" Ноутбук ASUS Laptop 14 F415MA-EK582W серый', 'asus-laptop-14',
        '14" Ноутбук ASUS Laptop 14 F415MA-EK582W серый [Full HD (1920x1080), TN+film, Intel Celeron N4020, ' ||
        'ядра: 2 х 1.1 ГГц, RAM 4 ГБ, SSD 128 ГБ, Intel UHD Graphics , Windows 11 Home Single Language]', false, 24299,
        0, '2022-12-18', (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),
       ('15.6" Ноутбук Acer Extensa 15 EX215-31-C3FF черный', 'laptop-acer-extensa-15',
        '15.6" Ноутбук Acer Extensa 15 EX215-31-C3FF черный [Full HD (1920x1080), TN+film, Intel Celeron N4020, ' ||
        'ядра: 2 х 1.1 ГГц, RAM 4 ГБ, SSD 128 ГБ, Intel UHD Graphics , без ОС]', true, 24299, 0, '2022-12-10',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),
       ('15.6" Ноутбук HP 250 G7 серый', 'laptop-hp-250-g7',
        '15.6" Ноутбук HP 250 G7 серый [Full HD (1920x1080), SVA (TN+film), Intel Celeron N4020, ядра: 2 х 1.1 ГГц, ' ||
        'RAM 4 ГБ, SSD 128 ГБ, Intel UHD Graphics 600 , без ОС]', true, 25199, 0, '2022-12-11',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),
       ('15.6" Ноутбук Lenovo IdeaPad 3 15ADA05 серый', 'laptop-lenovo-ideapad-3-15ada05',
        '15.6" Ноутбук Lenovo IdeaPad 3 15ADA05 серый [HD (1366x768), TN+film, AMD Athlon Silver 3050U, ' ||
        'ядра: 2 х 2.3 ГГц, RAM 4 ГБ, HDD 1000 ГБ, AMD Radeon Graphics , без ОС]', true, 25199, 0, '2022-12-14',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),
       ('14" Ноутбук ASUS VivoBook 14 E410MA-BV1502W черный', 'laptop-asus-vivobook-14',
        '14" Ноутбук ASUS Vivobook Go 14 E410MA-BV1521W черный [HD (1366x768), TN+film, Intel Pentium Silver N5030, ' ||
        'ядра: 4 х 1.1 ГГц, RAM 4 ГБ, eMMC 128 ГБ, Intel UHD Graphics 605 , Windows 11 Home Single', true, 26099, 0,
        '2022-12-17', (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),
       ('15.6" Ноутбук Acer Aspire 3 A315-23-R3LH черный', 'laptop-acer-aspire-3',
        '15.6" Ноутбук Acer Aspire 3 A315-23-R3LH черный [Full HD (1920x1080), TN+film, AMD Athlon Silver 3050U, ' ||
        'ядра: 2 х 2.3 ГГц, RAM 4 ГБ, SSD 256 ГБ, AMD Radeon Graphics , без ОС]', true, 26999, 0, '2022-12-15',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Ноутбуки')),

       ('Видеокарта PowerColor AMD Radeon R7 240', 'powercolor-amd-radeon-r7-240-graphics-card',
        'Видеокарта PowerColor AMD Radeon R7 240 [AXR7 240 2GBD5-HLEV2] [PCI-E 3.0 2 ГБ GDDR5, 64 бит, DVI-D, HDMI, ' ||
        'GPU 730 МГц]', true, 4899, 1000, '2022-12-19',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Видеокарты')),
       ('Видеокарта MSI GeForce RTX 3060 Ti GAMING Z TRIO', 'msi-geforce-rtx-3060-ti-gaming-z-trio-graphics-card',
        'Видеокарта MSI GeForce RTX 3060 Ti GAMING Z TRIO (LHR) [RTX 3060 Ti GAMING Z TRIO 8G LHR] ' ||
        '[PCI-E 4.0 8 ГБ GDDR6, 256 бит, DisplayPort x3, HDMI, GPU 1410 МГц]', true, 44999, 50, '2022-12-18',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Видеокарты')),

       ('ПК ASUS ROG Strix G15CE-1170KF0770', 'pc-asus-rog-strix-g15ce-1170kf0770',
        'ПК ASUS ROG Strix G15CE-1170KF0770 [90PF02P2-M005H0] [Intel Core i7-11700F, 8x2.5 ГГц, 32 ГБ DDR4, ' ||
        'GeForce RTX 3080, SSD 1000 ГБ, без ОС]', true, 227999, 20, '2022-12-17',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Персональные компьютеры')),

       ('Монитор AOC E2270SWN черный', 'monitor-aoc-e2270swn-black',
        '21.5" Монитор AOC E2270SWN черный [1920x1080@60 Гц, TN, 5 мс, 700 : 1, 200 Кд/м², 90°/65°, VGA (D-Sub)]', true, 6999, 20, '2022-12-22',
        (SELECT id FROM subcategory_t WHERE subcategory_name = 'Периферия'));


INSERT INTO image_t (file_name, product_id)
VALUES ('laptop-digma-eve-14-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-1.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-2.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-3.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-digma-eve-14-4.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Digma EVE 14 C411 серый')),
       ('laptop-irbis-nb77-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '13.3" Ноутбук Irbis NB77 черный')),
       ('laptop-prestigio-smartbook-133-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '14.1" Ноутбук Prestigio SmartBook 133 C4 серый')),
       ('asus-laptop-11-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '11.6" Ноутбук ASUS Laptop 11 E210MA-GJ151T черный')),
       ('laptop-lenovo-v11-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '14" Ноутбук Lenovo V14 IGL серый')),
       ('asus-laptop-14-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '14" Ноутбук ASUS Laptop 14 F415MA-EK582W серый')),
       ('laptop-acer-extensa-15-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '15.6" Ноутбук Acer Extensa 15 EX215-31-C3FF черный')),
       ('laptop-hp-250-g7-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '15.6" Ноутбук HP 250 G7 серый')),
       ('laptop-lenovo-ideapad-3-15ada05-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '15.6" Ноутбук Lenovo IdeaPad 3 15ADA05 серый')),
       ('laptop-asus-vivobook-14-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '14" Ноутбук ASUS VivoBook 14 E410MA-BV1502W черный')),
       ('laptop-acer-aspire-3-main.jpg',
        (SELECT id FROM product_t WHERE product_name = '15.6" Ноутбук Acer Aspire 3 A315-23-R3LH черный')),

       ('powercolor-amd-radeon-r7-240-graphics-card-main.jpg',
        (SELECT id FROM product_t WHERE product_name = 'Видеокарта PowerColor AMD Radeon R7 240')),
       ('msi-geforce-rtx-3060-ti-gaming-z-trio-graphics-card-main.jpg',
        (SELECT id FROM product_t WHERE product_name = 'Видеокарта MSI GeForce RTX 3060 Ti GAMING Z TRIO')),

       ('pc-asus-rog-strix-g15ce-1170kf0770-main.jpg',
        (SELECT id FROM product_t WHERE product_name = 'ПК ASUS ROG Strix G15CE-1170KF0770')),

       ('monitor-aoc-e2270swn-black-main.jpg',
        (SELECT id FROM product_t WHERE product_name = 'Монитор AOC E2270SWN черный'));
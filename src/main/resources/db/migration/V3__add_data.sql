INSERT INTO category_t (category_name, cipher, file_name)
VALUES ('Компьютерная техника', 'computer-technology', 'computer technology.png'),
       ('Комплектующие для ПК', 'pc-accessories', 'PC accessories.png'),
       ('Смартфоны и фототехника', 'smartphones-and-photo-equipment', 'Smartphones and photo equipment.png'),
       ('ТВ, консоли и аудио', 'tv-consoles-and-audio', 'TV, consoles and audio.png');

INSERT INTO subcategory_t (subcategory_name, cipher, file_name, category_id)
VALUES ('Ноутбуки и аксессуары', 'laptops-and-accessories', 'Laptops and accessories.jpg',
        (SELECT id FROM category_t WHERE category_name='Компьютерная техника')),
       ('Компьютеры и ПО', 'computers-and-software', 'Computers and software.jpg',
        (SELECT id FROM category_t WHERE category_name='Компьютерная техника')),
       ('Периферия и аксессуары', 'peripherals-and-accessories', 'Peripherals and accessories.jpg',
        (SELECT id FROM category_t WHERE category_name='Компьютерная техника'));
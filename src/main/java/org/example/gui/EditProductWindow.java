package org.example.gui;

import org.example.dao.ProductDao;
import org.example.model.Product;

import javax.swing.*;
import java.awt.*;

public class EditProductWindow {
    private JTextField nameField;
    private JTextField quantityField;

    public EditProductWindow(ProductDao productDao) {
        JFrame frame = new JFrame("Редагувати товар");
        nameField = new JTextField(20);
        quantityField = new JTextField(20);
        JButton editButton = new JButton("Зберегти зміни");

        editButton.addActionListener(e -> {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            Product product = productDao.getProductByName(name);
            if (product != null) {
                product.setQuantity(quantity);
                productDao.update(product);
                // Вивід повідомлення про успішне редагування
                JOptionPane.showMessageDialog(null, "Товар відредаговано успішно");
                // Закриття вікна після редагування
                frame.dispose();
            } else {
                // Вивід повідомлення, якщо продукт не знайдено
                JOptionPane.showMessageDialog(null, "Товар не знайдено");
            }
        });

        frame.setLayout(new GridLayout(3, 2));
        frame.add(new JLabel("Назва:"));
        frame.add(nameField);
        frame.add(new JLabel("Кількість:"));
        frame.add(quantityField);
        frame.add(editButton);

        frame.pack();
        frame.setVisible(true);
    }
}

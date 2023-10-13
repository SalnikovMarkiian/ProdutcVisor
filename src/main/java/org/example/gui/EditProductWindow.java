package org.example.gui;

import org.example.dao.ProductDao;
import org.example.model.Product;

import javax.swing.*;
import java.awt.*;

public class EditProductWindow {
    private JTextField nameField;
    private JTextField quantityField;
    private JFrame frame;

    public EditProductWindow(ProductDao productDao, Product product) {
        frame = new JFrame("Редагувати одиницю");
        nameField = new JTextField(product.getName(), 20);
        quantityField = new JTextField(String.valueOf(product.getQuantity()), 20);
        JButton editButton = new JButton("Зберегти зміни");

        editButton.addActionListener(e -> {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            product.setName(name);
            product.setQuantity(quantity);
            productDao.update(product);
            // Вивід повідомлення про успішне редагування
            JOptionPane.showMessageDialog(null, "Не забудь оновити список!");
            // Закриття вікна після редагування
            frame.dispose();
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

    public void closeWindow() {
        frame.dispose();
    }
}

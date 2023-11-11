/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.NhanVien.Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author hnhut
 */
public class UI_nhanVien {

    public static class mouseHover_transColor extends MouseAdapter {

        private Component this_MinhNhut;
        private Component[] cpActive;
        private Color cEnter;
        private Color cExit;

        public mouseHover_transColor(Color cEnter, Color cExit, Component this_MinhNhut, Component... cActive) {
            this.cpActive = cActive;
            this.cEnter = cEnter;
            this.cExit = cExit;
            this.this_MinhNhut = this_MinhNhut;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

            for (Component component : cpActive) {
                component.setForeground(cEnter);
            }
            this_MinhNhut.setSize(this_MinhNhut.getWidth() + 20, this_MinhNhut.getHeight() + 10);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            for (Component component : cpActive) {
                component.setForeground(cExit);
            }
            this_MinhNhut.setSize(this_MinhNhut.getWidth() - 20, this_MinhNhut.getHeight() - 10);
        }
    }

    public static mouseHover_transColor getAction(Color cEnter, Color Exit, Component this_MinhNhut, Component... cActive) {
        return new mouseHover_transColor(cEnter, Exit, this_MinhNhut, cActive);
    }
}

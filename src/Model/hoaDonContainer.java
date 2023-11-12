/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package Model;

import java.util.List;

/**
 *
 * @author HUNG
 */
public class hoaDonContainer {

    private hoaDon hd;
    private List<Object[]> list;

    public hoaDonContainer(hoaDon hd, List<Object[]> list) {
        this.hd = hd;
        this.list = list;
    }

    public hoaDonContainer() {
    }

    public hoaDon getHd() {
        return hd;
    }

    public void setHd(hoaDon hd) {
        this.hd = hd;
    }

    public List<Object[]> getList() {
        return list;
    }

    public void setList(List<Object[]> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "hoaDonContainer{" + "hd=" + hd + ", list=" + list + '}';
    }

}

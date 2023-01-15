/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cycle5;

import org.junit.Assert;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;



/**
 *
 * @author qartigala
 */
public class RecherchesTriTest {
    @Test
    public void testTriParSelection(){
        int[] tab = new int[]{1, 4, 2, 9, 2, 5, 1};
        int[] tab2 = new int[]{1, 1, 2, 2, 4, 5, 9};
        RecherchesTri.triParSelection(tab, tab.length);
        assertArrayEquals(tab2, tab);
        int[] tab3 = new int[]{-1, 7, -3, -9, -9};
        int[] tab4 = new int[]{-9, -9, -3, -1, 7};
        RecherchesTri.triParSelection(tab3, tab3.length);
        assertArrayEquals(tab4, tab3);
        int[] tab5 = new int[]{0};
        int[] tab6 = new int[]{0};
        RecherchesTri.triParSelection(tab5, tab5.length);
        assertArrayEquals(tab6, tab5);
    }
    @Test
    public void testEchanger(){
        int[] tab = new int[]{1, 4, 2, 9, 2, 5, 1};
        int[] tab2 = new int[]{2, 4, 1, 9, 2, 5, 1};
        RecherchesTri.echanger(tab, 0, 2);
        assertArrayEquals(tab2, tab);
        int[] tab3 = new int[]{0};
        int[] tab4 = new int[]{0};
        RecherchesTri.echanger(tab, 0, 0);
        assertArrayEquals(tab4, tab3);
    }
    @Test
    public void testRecherchePour(){
        int[] tab = new int[]{1, 4, 2, 9, 2, 5, 1};
        IndiceComparaison resul = RecherchesTri.recherchePour(tab, tab.length, 5);
        assertEquals(7, resul.comparaison, 0.1);
        assertEquals(5, resul.indice, 0.1);
    }
    @Test
    public void testRechercheTantQue(){
        int[] tab = new int[]{1, 4, 2, 9, 2, 5, 1};
        IndiceComparaison resul = RecherchesTri.rechercheTantQue(tab, tab.length, 5);
        assertEquals(5, resul.comparaison, 0.1);
        assertEquals(5, resul.indice, 0.1);
    }
    @Test
    public void testRechercheDicho(){
        int[] tab = new int[]{1, 2, 5, 8, 10, 13, 15};
        IndiceComparaison resul = RecherchesTri.rechercheDichotomique(tab, tab.length, 5);
        assertEquals(4, resul.comparaison, 0.1);
        assertEquals(2, resul.indice, 0.1);
    }
    
}

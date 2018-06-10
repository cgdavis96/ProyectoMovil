package mx.edu.itlp.proyectomovil.adaptadores;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;

import Objetos.OProducto;
import mx.edu.itlp.proyectomovil.R;

/**
 * Created by Jhosef Davis on 03/06/2018.
 */

public class productosAdaptador extends  BaseAdapter{
    public OProducto[] oProductos;
    private Context context;
    ViewHolder[] viewHolders;
    TextView textView;
    float totalFinal;
    public productosAdaptador(OProducto[] oProductos, Context context, TextView textView) {
        this.textView = textView;
        this.oProductos = oProductos;
        this.context = context;
        viewHolders = new ViewHolder[oProductos.length];
        this.textView.setText("Total $0");
    }

    public ViewHolder[] getViewHolders() {
        return viewHolders;
    }

    public float getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(float totalFinal) {
        this.totalFinal = totalFinal;
    }

    @Override
    public int getCount() {
        if (oProductos!=null)
            return oProductos.length;
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return (Object)oProductos[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final OProducto oProducto = oProductos[i];
        LayoutInflater f = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = f.inflate(R.layout.list_productos, null);
        final ViewHolder seleccion;
        TextView txtNombre = (TextView)v.findViewById(R.id.txtNombreLP);
        TextView txtDesc= (TextView)v.findViewById(R.id.txtDescripLP);
        TextView txtPrecio = (TextView)v.findViewById(R.id.txtPrecioLP);
        TextView txtDisp = (TextView)v.findViewById(R.id.txtDisLP);
        txtNombre.setText(oProducto.getNombre());
        txtDesc.setText(oProducto.getDescripcion());
        txtPrecio.setText(oProducto.getPrecio());
        txtDisp.setText(String.valueOf(oProducto.getStock()));
        final TextView subTotal = v.findViewById(R.id.txtSubtotal);
        //final EditText cantidad = v.findViewById(R.id.editCant);
        final ScrollableNumberPicker scrollableNumberPicker;
        scrollableNumberPicker = v.findViewById(R.id.number_picker_horizontal);

        seleccion = viewHolders[i] = viewHolders[i] == null ? new ViewHolder() : viewHolders[i];

        scrollableNumberPicker.setMinValue(0);
        scrollableNumberPicker.setMaxValue(Integer.valueOf(oProducto.getStock()));

        scrollableNumberPicker.setValue(seleccion.cantidad);
        ImageView imagen =(ImageView) v.findViewById(R.id.imvPro);
        imagen.setImageResource(R.drawable.burrito);            //poner url de origen, posiblemente tenga que salvarse como la cantidad
        subTotal.setText(String.format("$%,.2f", Float.valueOf(oProducto.getPrecio()) * seleccion.cantidad));
        ImageView menos = (ImageView) scrollableNumberPicker.getButtonMinusView();
        ImageView mas = (ImageView) scrollableNumberPicker.getButtonPlusView();
        seleccion.idPro = oProducto.getIdPro();
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollableNumberPicker.setValue(scrollableNumberPicker.getValue()-1);
                int Cant = scrollableNumberPicker.getValue();
                seleccion.cantidad = Cant;
                seleccion.total =  Float.valueOf(oProducto.getPrecio()) * seleccion.cantidad;
                subTotal.setText(String.format("$%,.2f", seleccion.total));
                sumar();
                textView.setText("Total $"+String.valueOf(totalFinal));

            }
        });
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollableNumberPicker.setValue(scrollableNumberPicker.getValue()+1);
                int Cant = scrollableNumberPicker.getValue();
                seleccion.cantidad = Cant;
                seleccion.total =  Float.valueOf(oProducto.getPrecio()) * seleccion.cantidad;
                subTotal.setText(String.format("$%,.2f", seleccion.total));
                sumar();
                textView.setText("Total $"+String.valueOf(totalFinal));
            }
        });

        return v;
    }

    public void sumar(){
        totalFinal = 0;
        for(int i=0;i<viewHolders.length;i++){
            if(viewHolders[i]!=null && viewHolders[i].getCantidad()>0){
                totalFinal += viewHolders[i].getTotal();
            }
        }
    }

    public class ViewHolder {
        String idPro = "";
        float total = 0;
        int cantidad=0;

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }

        public String getIdPro() {
            return idPro;
        }

        public void setIdPro(String idPro) {
            this.idPro = idPro;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }

}

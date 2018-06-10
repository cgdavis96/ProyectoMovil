package mx.edu.itlp.proyectomovil.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import Objetos.OProducto;
import mx.edu.itlp.proyectomovil.R;
import mx.edu.itlp.proyectomovil.editarProducto;

public class productosVendedorAdaptador extends BaseAdapter {
    public OProducto[] oProductos;
    private Context context;
    productosVendedorAdaptador.ViewHolder[] viewHolders;
    float totalFinal;
    public productosVendedorAdaptador(OProducto[] oProductos, Context context) {

        this.oProductos = oProductos;
        this.context = context;
        viewHolders = new productosVendedorAdaptador.ViewHolder[oProductos.length];

    }

    public productosVendedorAdaptador.ViewHolder[] getViewHolders() {
        return viewHolders;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final OProducto oProducto = oProductos[i];
        LayoutInflater f = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = f.inflate(R.layout.productos_vendedor, null);
        final productosVendedorAdaptador.ViewHolder seleccion;
        TextView txtNombre = (TextView)v.findViewById(R.id.txtNombreLP);
        TextView txtDesc= (TextView)v.findViewById(R.id.txtDescripLP);
        TextView txtPrecio = (TextView)v.findViewById(R.id.txtPrecioLP);
        TextView txtDisp = (TextView)v.findViewById(R.id.txtDisLP);
        txtNombre.setText(oProducto.getNombre());
        txtDesc.setText(oProducto.getDescripcion());
        txtPrecio.setText(oProducto.getPrecio());
        txtDisp.setText(String.valueOf(oProducto.getStock()));
        final TextView subTotal = v.findViewById(R.id.txtSubtotal);

        seleccion = viewHolders[i] = viewHolders[i] == null ? new productosVendedorAdaptador.ViewHolder() : viewHolders[i];


        ImageView imagen =(ImageView) v.findViewById(R.id.imvPro);
        imagen.setImageResource(R.drawable.burrito);


        seleccion.idPro = oProducto.getIdPro();
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,editarProducto.class);
                intent.putExtra("idPro",oProductos[i].getIdPro());
                context.startActivity(intent);
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
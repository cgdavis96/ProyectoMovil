package mx.edu.itlp.proyectomovil.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Objetos.Vendedor;
import mx.edu.itlp.proyectomovil.Login;
import mx.edu.itlp.proyectomovil.Productos;
import mx.edu.itlp.proyectomovil.R;


/**
 * Created by Jhosef Davis on 03/06/2018.
 */

public class vendedoresAdaptador extends BaseAdapter {

    public Vendedor[] vendedores;
    Intent intent;
    Context context;

    public vendedoresAdaptador(Vendedor []vendedores, Context context){
        this.vendedores=vendedores;
        this.context=context;
    }

    @Override
    public int getCount() {
        if (vendedores!=null)
            return vendedores.length;
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return (Object)vendedores[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater f= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=f.inflate(R.layout.list_vendedores,null);
        TextView txtNomVen=(TextView)v.findViewById(R.id.txtNomVen);
        TextView txtTelVen=(TextView)v.findViewById(R.id.txtTelVen);

        ImageView imageView = (ImageView) v.findViewById(R.id.imvVende);

        //imageView.setImageResource(resId);

        txtNomVen.setText(vendedores[position].getNombre()+" "+vendedores[position].getApellidoP()+" "+vendedores[position].getApellidoM());
        txtTelVen.setText(vendedores[position].getNumero());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Productos.class);
                intent.putExtra("idVen",vendedores[position].getIdVen());
                context.startActivity(intent);
            }
        });

        return v;
    }

}


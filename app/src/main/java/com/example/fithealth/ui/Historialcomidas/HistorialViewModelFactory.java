package com.example.fithealth.ui.Historialcomidas;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.fithealth.datos.AlimentoRepository;

public class HistorialViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final AlimentoRepository mRepository;

    public HistorialViewModelFactory(AlimentoRepository repository) {
        this.mRepository = repository;
    }

    //Se sobreescribe el método oncreate
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new HistorialViewModel(mRepository);
    }
}

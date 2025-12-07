package com.spygstudios.spyglib.datacontainer.types;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

class ByteArrayListType implements PersistentDataType<String, List<byte[]>> {

    private static final String DELIMITER = ";";

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<List<byte[]>> getComplexType() {
        return (Class<List<byte[]>>) (Class<?>) List.class;
    }

    @Override
    public String toPrimitive(List<byte[]> complex, PersistentDataAdapterContext context) {
        if (complex == null || complex.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        Base64.Encoder encoder = Base64.getEncoder();

        for (int i = 0; i < complex.size(); i++) {
            byte[] bytes = complex.get(i);
            if (bytes != null) {
                builder.append(encoder.encodeToString(bytes));
            }
            if (i < complex.size() - 1) {
                builder.append(DELIMITER);
            }
        }

        return builder.toString();
    }

    @Override
    public List<byte[]> fromPrimitive(String primitive, PersistentDataAdapterContext context) {
        if (primitive == null || primitive.isEmpty()) {
            return new ArrayList<>();
        }

        String[] encoded = primitive.split(DELIMITER);
        List<byte[]> result = new ArrayList<>(encoded.length);
        Base64.Decoder decoder = Base64.getDecoder();

        for (String encodedBytes : encoded) {
            if (!encodedBytes.isEmpty()) {
                result.add(decoder.decode(encodedBytes));
            }
        }

        return result;
    }
}